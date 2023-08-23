package com.demo.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.state.ScreenState
import com.demo.todo.util.ScreenEvents
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProgressViewModel(
    val progressRepository: ProgressRepository
):ViewModel() {

    private val _timeCount = MutableStateFlow(15)

    @OptIn(DelicateCoroutinesApi::class)
    private val _todayState = progressRepository
        .getTodayProgressData()
        .stateIn(viewModelScope , SharingStarted.WhileSubscribed(), null)

    private val _screenState = MutableStateFlow(ScreenState())

    val screenState = combine(_screenState , _todayState){ screenState , todayState ->
        screenState.copy(
            timerCurrentValue = todayState?.completedMinutes ?: 1500,
            timerOverAllValue = todayState?.totalMinutes ?: 1500,
            todoList = todayState?.todoList ?: mutableListOf()
        )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000), ScreenState())

    @OptIn(DelicateCoroutinesApi::class)
    fun onEvent(
        event : ScreenEvents
    ){
        when(event){
            is ScreenEvents.NewToDoList -> {
                var progressData : ProgressData? = null
                viewModelScope.launch {
                    if (_todayState.value == null)
                        progressRepository.insertProgressData(ProgressData())
                    progressData = _todayState.value
                    progressData?.todoList?.add(event.toDoList)
                    progressRepository.insertProgressData(progressData)
                }
                _screenState.update {
                    it.copy(
                        todoList = progressData?.todoList ?: mutableListOf()
                    )
                }
            }
            ScreenEvents.TimerRestart -> {

            }
            ScreenEvents.TimerStart -> {
                _screenState.update {it.copy(
                    timerIsOn = true
                )
                }
                timerStart()
            }
            ScreenEvents.TimerStop -> {
                _screenState.update {it.copy(
                    timerIsOn = false
                )
                }
            }

            is ScreenEvents.CheckBoxChange -> {
                val progressData = _todayState.value!!.todoList
                progressData[event.index].isCompleted = event.isCheck
                viewModelScope.launch {
                    progressRepository.insertProgressData(
                        _todayState.value!!.copy(
                            todoList = progressData
                        )
                    )
                }
            }

            is ScreenEvents.DeleteTodo -> {
                val progressData = _todayState.value!!.todoList
                progressData.removeAt(event.index)
                viewModelScope.launch {
                    progressRepository.insertProgressData(
                        _todayState.value!!.copy(
                            todoList = progressData
                        )
                    )
                    _screenState.update {
                        it.copy(
                            todoList = progressData
                        )
                    }
                }
            }
        }
    }
    private fun timerStart(){
        if (screenState.value.timerIsOn) return
        viewModelScope.launch {
            while (screenState.value.timerIsOn){
                val progressPercentage = (screenState.value.timerOverAllValue.toFloat()) /
                        (screenState.value.timerCurrentValue).toFloat()
                delay(1000)
                val difference =  1
                val stringTime = (difference / 60).toString() +':'+(difference / 60).toString()
                _screenState.update {
                    it.copy(
                        timerCurrentValue = it.timerCurrentValue + 1,
                        progressPercentage = progressPercentage,
                    )
                }
            }
        }
    }
}
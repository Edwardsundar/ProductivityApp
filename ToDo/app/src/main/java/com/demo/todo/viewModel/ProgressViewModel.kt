package com.demo.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.todo.resorce.Resorce
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.state.ScreenState
import com.demo.todo.util.ScreenEvents
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProgressViewModel @OptIn(DelicateCoroutinesApi::class) constructor(
    val progressRepository: ProgressRepository
):ViewModel() {

    private val _timeCount = MutableStateFlow(15)

    @OptIn(DelicateCoroutinesApi::class)
    private val _todayState = progressRepository
        .getTodayProgressData()
        .stateIn(viewModelScope , SharingStarted.WhileSubscribed(), null)

    private val _screenState = MutableStateFlow(ScreenState())

    @OptIn(DelicateCoroutinesApi::class)
    val allData = progressRepository
        .getAllData()
        .stateIn(viewModelScope , SharingStarted.WhileSubscribed() , emptyList())

    val screenState = combine(_screenState , _todayState ){ screenState , todayState ->
        screenState.copy(
            timerOverAllValue =  screenState.timerOverAllValue,
            timerCurrentValue = screenState.timerCurrentValue,
            progressPercentage =  screenState.progressPercentage,
            todoList =  todayState?.todoList ?: screenState.todoList
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
                    if (_todayState.value == null) {
                        _screenState.update {
                            it.copy(
                                stringTime = "Edward"
                            )
                        }
                        progressRepository.insertProgressData(Resorce.getDefaultData())
                    }
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
                _screenState.update {
                    it.copy(
                        timerCurrentValue = 0,
                        timerIsOn = false,
                        progressPercentage = 1f
                    )
                }
                viewModelScope.launch {
                    val progressData = _todayState.value!!.copy(
                        progressPercentage = 1f,
                        completedMinutes = 0
                    )
                    progressRepository.insertProgressData(progressData)
                }
            }
            ScreenEvents.TimerStart -> {
                _screenState.update {it.copy(
                    timerIsOn = true,
                    timerCurrentValue = _todayState.value?.completedMinutes ?: 0,
                    progressPercentage = _todayState.value?.progressPercentage ?: 1f
                )
                }
                timerStart()
            }
            ScreenEvents.TimerStop -> {
                _screenState.update {it.copy(
                    timerIsOn = false,
                )
                }
                viewModelScope.launch {
                    var progressData = _todayState.value
                    if (progressData == null)
                        this.cancel()
                    progressData = progressData!!.copy(
                        completedMinutes = _screenState.value.timerCurrentValue,
                        progressPercentage = _screenState.value.progressPercentage
                    )
                    progressRepository.insertProgressData(progressData)
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
        if (!screenState.value.timerIsOn) return
        viewModelScope.launch {
            while (screenState.value.timerIsOn){
                val difference =  (screenState.value.timerOverAllValue - _screenState.value.timerCurrentValue)
                val progressPercentage = difference /
                        (screenState.value.timerOverAllValue.toFloat())
                if (difference <= 0L || !screenState.value.timerIsOn) {
                    onEvent(ScreenEvents.TimerStop)
                    if(difference == 0L)
                        _screenState.update {
                            it.copy(
                                stringTime = "Completed 🏆\uD83C\uDF96️"
                            )
                        }
                    this.cancel()
                }
                delay(1000)
                val stringTime = (difference / 60).toString() +':'+(difference % 60).toString()
                _screenState.update {
                    it.copy(
                        timerCurrentValue = it.timerCurrentValue + 1,
                        progressPercentage = progressPercentage,
                        stringTime = stringTime
                    )
                }
            }
        }
    }
}
package com.demo.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.todo.resorce.TaskPriority
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.roomDataBase.ToDoList
import com.demo.todo.state.ScreenState
import com.demo.todo.util.ScreenEvents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProgressViewModel(
    progressRepository: ProgressRepository
):ViewModel() {

    private val _timeCount = MutableStateFlow<Int>(15)

    private val _todayState = progressRepository
        .getTodayProgressData()
        .stateIn(viewModelScope , SharingStarted.WhileSubscribed(), null)
    private val _screenState = MutableStateFlow(ScreenState())

    val screenState = combine(_screenState , _todayState){ screenState , todayState ->
        screenState.copy(
            timerCurrentValue = todayState?.completedMinutes ?: 1500 ,
            timerOverAllValue = todayState?.totalMinutes ?: 1500 ,
            todoList = todayState?.todoList ?: mutableListOf()
        )
    }.stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000), null)

    fun onEvent(
        event : ScreenEvents
    ){
        when(event){
            is ScreenEvents.NewToDoList -> {
                _todayState.value?.todoList?.add(
                    ToDoList(
                        title = event.title,
                        priority = event.priority ,
                        isCompleted = false
                    )
                )
            }
            ScreenEvents.TimerRestart -> TODO()
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
        }
    }
    private fun timerStart(){
        if (screenState.value?.timerIsOn == false) return
        viewModelScope.launch {
            while (screenState.value?.timerIsOn == true){
                delay(1000)
                _screenState.update {
                    it.copy(
                        timerCurrentValue = it.timerCurrentValue - 1
                    )
                }
            }
        }
    }
}
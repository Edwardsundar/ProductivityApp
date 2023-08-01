package com.demo.todo.viewModel

import androidx.lifecycle.ViewModel
import com.demo.todo.resorce.Resorce.Companion.date
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.roomDataBase.ProgressDataBase
import com.demo.todo.roomDataBase.ToDoList
import kotlinx.coroutines.flow.Flow

class ProgressRepository(
    val dataBase: ProgressDataBase
) {

    fun getTodayProgressData(): Flow<ProgressData> {
        dataBase.let {
            return it.progressDao().getTodayProgressData(date)
        }
    }

}
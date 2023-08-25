package com.demo.todo.viewModel

import android.provider.Settings.Global
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.demo.todo.resorce.Resorce
import com.demo.todo.resorce.Resorce.Companion.getTodayDate
import com.demo.todo.roomDataBase.ProgressData
import com.demo.todo.roomDataBase.ProgressDataBase
import com.demo.todo.roomDataBase.ProgressDataDao
import com.demo.todo.roomDataBase.ToDoList
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class ProgressRepository(
    private val dataBase: ProgressDataDao
) {
    fun getTodayProgressData(): Flow<ProgressData> {
        return dataBase.getTodayProgressData()
    }
    suspend fun createToday(){
        insertProgressData(Resorce.getDefaultData())
    }
    suspend fun insertProgressData(progressData: ProgressData?){
        if (progressData != null)
            dataBase.insertProgressData(progressData)
    }
}
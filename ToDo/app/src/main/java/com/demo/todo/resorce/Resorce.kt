package com.demo.todo.resorce


import android.icu.text.SimpleDateFormat
import androidx.compose.ui.graphics.Color
import com.demo.todo.roomDataBase.ProgressData
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Resorce {
    companion object{
        fun getTodayDate():String{
            val calander = Date()
            val dateFormate = SimpleDateFormat("dd-MM-yy",Locale.getDefault())
            return dateFormate.format(calander).toString()
        }
       fun getYesterdayDate():String{
           val calander = Calendar.getInstance()
           calander.add(Calendar.DAY_OF_YEAR,-1)
           val yesterDay = calander.time
           val dateFormate = SimpleDateFormat("dd-MM-yy",Locale.getDefault())
           return dateFormate.format(yesterDay).toString()
       }

        const val CALENDAR_ROW = 5
        const val CALENDAR_COLUMN = 7

        val priority = mutableListOf(
            Pair("General" , Color.Green),
            Pair("Medium" , Color.Yellow),
            Pair("Important" , Color.Red)
        )
        fun getDefaultData():ProgressData = ProgressData(
            todoList = mutableListOf(),
            totalMinutes = 1500,
            completedMinutes = 0,
            progressPercentage = 1f,
            key = getTodayDate()
        )
    }
}
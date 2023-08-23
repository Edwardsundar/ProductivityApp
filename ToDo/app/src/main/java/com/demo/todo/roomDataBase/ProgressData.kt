package com.demo.todo.roomDataBase

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.demo.todo.resorce.Resorce
import com.demo.todo.ui.theme.Easy

@Entity(tableName = "progressData")
data class ProgressData(
    @TypeConverters(TypeConverter::class)
    val todoList:MutableList<ToDoList> = mutableListOf(),
    val totalMinutes:Long = 1500,
    val completedMinutes:Long = 0,
    val progressPercentage : Float = 0f,// 0 to 1
    @PrimaryKey
    val key : String = Resorce.getTodayDate()
)

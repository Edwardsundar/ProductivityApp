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
    val todoList:MutableList<ToDoList> ,
    val totalMinutes:Long ,
    val completedMinutes:Long ,
    val progressPercentage : Float ,// 1 to 0
    @PrimaryKey
    val key : String
)

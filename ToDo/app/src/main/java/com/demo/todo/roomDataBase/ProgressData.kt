package com.demo.todo.roomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "progressData")
data class ProgressData(
    val color : Long,
    @TypeConverters(TypeConverter::class)
    val todoList:List<ToDoList>,
    val totalMinutes:Long,
    val completedMinutes:Long,
    val progressPercentage : Float,// 0 to 1
    @PrimaryKey
    val key : String
    )

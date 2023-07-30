package com.demo.todo.roomDataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun fromList(list : List<ToDoList>):String{
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(jsonString : String):List<ToDoList>{
        val type = object : TypeToken<List<ToDoList>>(){}.type
        val gson = Gson()
        return gson.fromJson(jsonString , type)
    }
}
package com.demo.todo.resorce

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Resorce {
    companion object{
        var date = SimpleDateFormat("dd-MM-yy" , Locale.getDefault()).format(Date())

        const val CALENDAR_ROW = 5
        const val CALENDAR_COLUMN = 7
    }
}
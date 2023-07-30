package com.demo.todo.navcontroller

sealed class NavRoot(val root:String){
    object CalendarViewNav:NavRoot("calendarViewNav")
    object HomeViewNav:NavRoot("homeViewNav")
    object ToDoViewNav:NavRoot("hoDoViewNav")
}

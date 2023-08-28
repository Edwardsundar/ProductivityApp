package com.demo.todo.navcontroller

sealed class NavRoot(val root:String){
    object CalendarViewNav:NavRoot("calendarViewNav")
    object GraphViewNav:NavRoot("graphViewNav")
    object HomeViewNav:NavRoot("homeViewNav")
    object ToDoViewNav:NavRoot("ToDoViewNav")
}

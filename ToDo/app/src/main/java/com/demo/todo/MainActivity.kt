package com.demo.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.todo.navcontroller.NavRoot
import com.demo.todo.roomDataBase.ProgressDataBase
import com.demo.todo.ui.theme.CalendarView
import com.demo.todo.ui.theme.DeepBlue
import com.demo.todo.ui.theme.ToDoListView.AddNewToDo
import com.demo.todo.ui.theme.ToDoListView.ToDoListView
import com.demo.todo.ui.theme.ToDoTheme
import com.demo.todo.ui.theme.bottomMenu.BottomMenuItem
import com.demo.todo.ui.theme.bottomMenu.CreateBottomMenu
import com.demo.todo.ui.theme.timerScreen.TimerScreen
import com.demo.todo.viewModel.ProgressRepository
import com.demo.todo.viewModel.ProgressViewModel
import com.demo.todo.viewModel.ViewModelProviderFactory

class MainActivity : ComponentActivity() {
    private var db = ProgressDataBase.getDataBase(applicationContext)

    val repository = ProgressRepository(db)
    val viewModelFactory = ViewModelProviderFactory(repository)

    var viewModel = ViewModelProvider(this , viewModelFactory).get(ProgressViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         //   val state by viewModel.state.collectAsState()
            ToDoTheme {
                var navController = rememberNavController()
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DeepBlue)
                ){
                    NavHost(
                        navController = navController,
                        startDestination = NavRoot.ToDoViewNav.root
                    ) {
                        composable(NavRoot.CalendarViewNav.root) {
                            CalendarView()
                        }
                        composable(NavRoot.ToDoViewNav.root) {
                            ToDoListView(navController)
                        }
                        composable(NavRoot.HomeViewNav.root) {
                            TimerScreen()
                        }
                    }
                    Spacer(modifier = Modifier.height(0.dp))
                    CreateBottomMenu(
                        bottomMenuItem = listOf(
                            BottomMenuItem(
                                title = "Date",
                                icon = painterResource(id = R.drawable.ic_calendar)
                            ),
                            BottomMenuItem(
                                title = "Home",
                                icon = painterResource(id = R.drawable.ic_home)
                            ),
                            BottomMenuItem(
                                title = "ToDo",
                                icon = painterResource(id = R.drawable.ic_todo)
                            )
                        ),
                        modifier = Modifier.align(Alignment.BottomCenter),
                        navController = navController
                    )
                }
            }
        }
    }
}
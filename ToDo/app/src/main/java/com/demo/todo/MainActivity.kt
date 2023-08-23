package com.demo.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.todo.navcontroller.NavRoot
import com.demo.todo.roomDataBase.ProgressDataBase
import com.demo.todo.ui.theme.CalendarView
import com.demo.todo.ui.theme.DeepBlue
import com.demo.todo.ui.theme.ToDoListView.ToDoListView
import com.demo.todo.ui.theme.ToDoTheme
import com.demo.todo.ui.theme.bottomMenu.BottomMenuItem
import com.demo.todo.ui.theme.bottomMenu.BottomView
import com.demo.todo.ui.theme.bottomMenu.CreateBottomMenu
import com.demo.todo.ui.theme.timerScreen.TimerScreen
import com.demo.todo.viewModel.ProgressRepository
import com.demo.todo.viewModel.ProgressViewModel
import com.demo.todo.viewModel.ViewModelProviderFactory
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = ProgressDataBase.getDataBase(applicationContext)
        val progressRepository = ProgressRepository(db.progressDao())
        val viewModelFactory = ViewModelProviderFactory(progressRepository)
        var viewModel = ViewModelProvider(this , viewModelFactory).get(ProgressViewModel::class.java)
        setContent {
            ToDoTheme {
                val viewModel = viewModel<ProgressViewModel>(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            return ProgressViewModel(progressRepository) as T
                        }
                    }
                )

                val bottomView by remember {
                    mutableStateOf(BottomView.ToDo)
                }

                val state by viewModel.screenState.collectAsState()

                val navController = rememberNavController()
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
                            ToDoListView(
                                navController = navController ,
                                state  = state,
                                onEvent = viewModel::onEvent)
                        }
                        composable(NavRoot.HomeViewNav.root) {
                            TimerScreen(
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                    val name = navController.graph.route.toString()
                    Spacer(modifier = Modifier.height(0.dp))
                    CreateBottomMenu(
                        bottomMenuItem = listOf(
                            BottomMenuItem(
                                title = name,
                                icon = painterResource(id = R.drawable.ic_calendar),
                                bottomView = BottomView.Calender
                            ),
                            BottomMenuItem(
                                title = "Home",
                                icon = painterResource(id = R.drawable.ic_home),
                                bottomView = BottomView.Home
                            ),
                            BottomMenuItem(
                                title = "ToDo",
                                icon = painterResource(id = R.drawable.ic_todo),
                                bottomView = bottomView
                            )
                        ),
                        modifier = Modifier.align(Alignment.BottomCenter),
                        navController = navController,
                        bottomView = BottomView.ToDo
                    )
                }
            }
        }
    }
}
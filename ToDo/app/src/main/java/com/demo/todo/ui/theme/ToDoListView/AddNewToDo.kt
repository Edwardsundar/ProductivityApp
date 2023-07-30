package com.demo.todo.ui.theme.ToDoListView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddNewToDo(
    modifier :Modifier = Modifier,
    dialogEvent : ()-> Unit
){

    var currentTitle by remember {
        mutableStateOf("")
    }
    var currentMinutes:Long?
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
                      dialogEvent()
        },
        title = { Text(text = "Addd Activity") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextField(
                    value = currentTitle,
                    onValueChange = {
                             currentTitle = it
                    },
                    placeholder = {
                        Text(text = "Title")
                    }
                )
                TextField(
                    value = "",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        currentMinutes = it.toLongOrNull()
                        if (currentMinutes != null){

                        }

                    },
                    placeholder = {
                        Text(text = "Minutes")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                Button(
                    onClick = {
                        dialogEvent()
                    }
                ) {
                    Text(text = "Saved")
                }
            }
        }
    )
}
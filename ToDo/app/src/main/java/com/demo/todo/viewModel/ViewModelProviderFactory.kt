package com.demo.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.todo.roomDataBase.ProgressDataBase

class ViewModelProviderFactory(
    val progressRepository : ProgressRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProgressViewModel(progressRepository) as T
    }
}
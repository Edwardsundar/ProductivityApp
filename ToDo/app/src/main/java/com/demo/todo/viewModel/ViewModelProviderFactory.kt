package com.demo.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class ViewModelProviderFactory(
    private val progressRepository : ProgressRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProgressViewModel(progressRepository) as T
    }
}
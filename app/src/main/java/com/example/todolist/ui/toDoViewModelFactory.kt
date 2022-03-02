package com.example.todolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.repositories.ToDoRepository

class ToDoViewModelFactory(
    private val repository: ToDoRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoViewModel(repository) as T
    }
}
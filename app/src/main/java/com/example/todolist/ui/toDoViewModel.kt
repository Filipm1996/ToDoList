package com.example.todolist.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.todolist.data.repositories.toDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class toDoViewModel(
    private val repository : toDoRepository
) : ViewModel() {

    fun insertActivity(activity: com.example.todolist.data.db.entities.Activity) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertActivity(activity)
    }

    fun deleteActivity(id : Int) = CoroutineScope(Dispatchers.IO).launch{repository.deleteActivity(id)}

    fun getAllActivities() = repository.getAllActivities()

    fun isCheckedChange(done: String, id : Int) = CoroutineScope(Dispatchers.IO).launch { repository.isCheckedChange(done, id)}

    fun getDataCount() = repository.getDataCount()
}
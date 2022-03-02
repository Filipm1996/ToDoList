package com.example.todolist.ui


import androidx.lifecycle.ViewModel
import com.example.todolist.data.repositories.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val repository : ToDoRepository
) : ViewModel() {

    fun insertActivity(activity: com.example.todolist.data.db.entities.Activity) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertActivity(activity)
    }

    fun deleteActivity(id : Int) = CoroutineScope(Dispatchers.IO).launch{repository.deleteActivity(id)}

    fun getAllActivities() = repository.getAllActivities()

    fun isCheckedChange(done: String, id : Int) = CoroutineScope(Dispatchers.IO).launch { repository.isCheckedChange(done, id)}

}
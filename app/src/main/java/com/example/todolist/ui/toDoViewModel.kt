package com.example.todolist.ui



import android.util.Log
import com.example.todolist.data.db.entities.Activity
import androidx.lifecycle.ViewModel
import com.example.todolist.data.repositories.ToDoRepository
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ToDoViewModel(
    private val repository : ToDoRepository
) : ViewModel() {


    fun saveActivityToFirebase(activity : Activity)  = repository.saveActivityToFirebase(activity)

    fun deleteActivityFromFirebase(description: String) = repository.deleteActivityFromFirebase(description)

    fun getAllActivitiesFromFirebase() = repository.getAllActivitiesFromFirebase()

    fun isCheckedChangeInFirebase(done: String, description: String) = repository.isCheckedChangeInFirebase(done,description)

    fun deleteAllActivities(list: MutableList<Activity>) = repository.deleteAllActivities(list)
}
package com.example.todolist.data.repositoriesde

import androidx.lifecycle.LiveData
import com.example.todolist.data.db.entities.Activity
import com.example.todolist.data.db.entities.ActivityDatabase

interface deafultRepository {
    suspend fun insertActivity(activity: Activity)

    fun deleteActivity (id : Int)

    fun getAllActivities() : LiveData<List<Activity>>

    fun isCheckedChange(done : String, id : Int)

    fun getDataCount() : Int
}
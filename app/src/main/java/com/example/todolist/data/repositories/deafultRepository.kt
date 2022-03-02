package com.example.todolist.data.repositories

import androidx.lifecycle.LiveData
import com.example.todolist.data.db.entities.Activity

interface DeafultRepository {
    suspend fun insertActivity(activity: Activity)

    fun deleteActivity (id : Int)

    fun getAllActivities() : LiveData<List<Activity>>

    fun isCheckedChange(done : String, id : Int)

    fun getDataCount() : Int
}
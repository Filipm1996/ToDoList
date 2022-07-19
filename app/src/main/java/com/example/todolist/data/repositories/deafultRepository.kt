package com.example.todolist.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.db.entities.Activity

interface DeafultRepository {

    fun saveActivityToFirebase (activity: Activity)

    fun deleteActivityFromFirebase (description: String)

    fun getAllActivitiesFromFirebase() : MutableLiveData<MutableList<Activity>>

    fun isCheckedChangeInFirebase(done: String, description: String)

    fun deleteAllActivities(list:MutableList<Activity>)
}
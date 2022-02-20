package com.example.todolist.data.repositories

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.db.entities.Activity
import com.example.todolist.data.db.entities.ActivityDatabase
import com.example.todolist.data.repositoriesde.deafultRepository

class toDoRepository (
    mContext:Context
        ) : deafultRepository {
    private val db = Room.databaseBuilder(mContext, ActivityDatabase::class.java, "activity_database").build()

    override suspend fun insertActivity(activity: Activity) = db.activityDao().insertActivity(activity)

    override fun deleteActivity(id: Int) =db.activityDao().deleteActivity(id)

    override fun getAllActivities() = db.activityDao().getAllActivities()

    override fun isCheckedChange(done: String, id: Int) =db.activityDao().isCheckedChange(done,id)

    override fun getDataCount(): Int = db.activityDao().getDataCount()
}
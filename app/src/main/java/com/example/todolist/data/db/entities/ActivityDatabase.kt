package com.example.todolist.data.db.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.data.db.ActivityDao

@Database(
    entities = [Activity::class],
    version = 1
)

abstract class ActivityDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao

}
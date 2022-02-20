package com.example.todolist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.data.db.entities.Activity

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Query("DELETE FROM list_of_activities WHERE id= :Id")
    fun deleteActivity(Id : Int)

    @Query("SELECT * FROM list_of_activities")
    fun getAllActivities(): LiveData<List<Activity>>

    @Query("UPDATE list_of_activities SET isDone= :done WHERE id= :Id")
    fun isCheckedChange(done : String, Id : Int)

    @Query("SELECT COUNT(id) FROM list_of_activities")
    fun getDataCount(): Int
}
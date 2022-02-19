package com.example.todolist

import androidx.room.*

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activity)

    @Query("DELETE FROM list_of_activities WHERE id= :Id")
    suspend fun deleteActivity(Id : Int)

    @Query("SELECT * FROM list_of_activities")
    suspend fun getAllActivities(): List<Activity>

    @Query("UPDATE list_of_activities SET isDone= :done WHERE id= :Id")
    suspend fun isCheckedChange(done : String, Id : Int)

    @Query("SELECT COUNT(id) FROM list_of_activities")
    suspend fun getDataCount(): Int
}
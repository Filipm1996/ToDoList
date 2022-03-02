package com.example.todolist.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_of_activities")
data class Activity(
    val description : String,
    val isDone : String = "false",
    @PrimaryKey(autoGenerate = true)
    val id: Int? =null
)





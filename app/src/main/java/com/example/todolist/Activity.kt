package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity(tableName = "list_of_activities")
data class Activity(
    val description : String,
    val isDone : String = "false",
    @PrimaryKey(autoGenerate = true)
    val id: Int? =null
)





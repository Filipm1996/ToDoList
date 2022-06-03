package com.example.todolist.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Activity(
    val description : String ="",
    val done : String = "false"
)





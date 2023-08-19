package com.route.todoapp.tasksdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todo-task")
data class Task(
    @PrimaryKey val userID: Int,
    val taskTitle: String? = null,
    val taskDescription: String? = null,

    val isDone: Boolean? = null
)

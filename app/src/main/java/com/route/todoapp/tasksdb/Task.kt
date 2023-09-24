package com.route.todoapp.tasksdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "todo-task")
data class Task(
    @PrimaryKey(autoGenerate = true) val userID: Int? = null,
    var taskTitle: String? = null,
    var taskDescription: String? = null,
    var taskDate: Long? = null,
    var isDone: Boolean? = false
) : Serializable

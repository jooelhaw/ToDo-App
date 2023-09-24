package com.route.todoapp.tasksdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todoapp.tasksdb.Task

@Dao
interface TaskDao {
    @Insert
    fun insertTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Update
    fun updateTask(task: Task)
    @Query("SELECT * FROM `todo-task`")
    fun getAll(): List<Task>
    @Query("SELECT * FROM `todo-task` WHERE taskDate LIKE :date")
    fun getByDate(date: Long): List<Task>
}
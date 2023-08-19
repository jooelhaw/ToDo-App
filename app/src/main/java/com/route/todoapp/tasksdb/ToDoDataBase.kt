package com.route.todoapp.tasksdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class ToDoDataBase : RoomDatabase(){
    abstract fun taskDao(): TaskDao
    companion object{
        private var todoDB_Instance: ToDoDataBase? = null
        fun getInstance(context: Context): ToDoDataBase{
            if (todoDB_Instance == null){
                todoDB_Instance = Room.databaseBuilder(context,ToDoDataBase::class.java,"todo").build()
            }
            return todoDB_Instance!!
        }
    }

}
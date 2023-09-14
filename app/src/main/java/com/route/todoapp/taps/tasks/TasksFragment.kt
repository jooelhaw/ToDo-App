package com.route.todoapp.taps.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todoapp.databinding.FragmentTasksBinding
import com.route.todoapp.tasksdb.Task
import com.route.todoapp.tasksdb.ToDoDataBase

class TasksFragment: Fragment() {
    lateinit var viewBinding: FragmentTasksBinding
    lateinit var adapter: TasksAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTasksBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
        initView()
        loadTasks()
    }

    fun loadTasks() {
        context?.let {
            var tasksList = ToDoDataBase.getInstance(it).taskDao().getAll()
            adapter.bindTask(tasksList)
        }

    }

    private fun initView() {
        adapter = TasksAdapter(null)
        viewBinding.recyclerTasks.adapter = adapter
    }

}
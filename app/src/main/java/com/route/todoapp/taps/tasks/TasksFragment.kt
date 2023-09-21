package com.route.todoapp.taps.tasks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todoapp.R
import com.route.todoapp.databinding.FragmentTasksBinding
import com.route.todoapp.databinding.ItemTasksBinding
import com.route.todoapp.tasksdb.Task
import com.route.todoapp.tasksdb.ToDoDataBase
import java.util.Calendar

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun refreshRecyclerView() {
        adapter.notifyDataSetChanged()
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
            adapter.onButtonClick = object : TasksAdapter.OnButtonClickListener{
                override fun onClick(position: Int, task: Task) {
                    task.isDone = true
                    ToDoDataBase.getInstance(it).taskDao().updateTask(task)
                    adapter.notifyItemChanged(position)
                    Toast.makeText(it,"Done",Toast.LENGTH_SHORT).show()
                    refreshRecyclerView()

                }
            }

        }

    }

    private fun initView() {
        adapter = TasksAdapter(null,context)
        viewBinding.recyclerTasks.adapter = adapter
        viewBinding.calendarView.setSelectedDate(CalendarDay.today())
    }

}
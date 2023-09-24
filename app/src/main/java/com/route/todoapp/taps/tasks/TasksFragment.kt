package com.route.todoapp.taps.tasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.route.todoapp.EditActivity
import com.route.todoapp.MainActivity
import com.route.todoapp.clearTime
import com.route.todoapp.databinding.FragmentTasksBinding
import com.route.todoapp.tasksdb.Task
import com.route.todoapp.tasksdb.ToDoDataBase
import java.text.SimpleDateFormat
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

    val calendar = Calendar.getInstance()
    init {
        calendar.clearTime()
    }

    private fun loadTasksByDate() {
        viewBinding.calendarView.setOnDateChangedListener( OnDateSelectedListener{
                widget, date, selected ->
            if (selected) {
                calendar.set(Calendar.YEAR, date.year)
                calendar.set(Calendar.MONTH, date.month-1)
                calendar.set(Calendar.DAY_OF_MONTH, date.day)
                var selectedDate: Long = calendar.timeInMillis
                MainActivity().calendar.timeInMillis = selectedDate
                loadTasks(selectedDate)
            }
        })
    }


    override fun onStart() {
        super.onStart()
        initView()
    }

    fun loadTasks(date: Long?) {
        context?.let {
            if (date!=null) {
                var tasksList = ToDoDataBase.getInstance(it).taskDao().getByDate(date)
                adapter.bindTask(tasksList)
            }
            // make task done implementation
            adapter.onButtonClick = object : TasksAdapter.OnButtonClickListener{
                override fun onClick(position: Int, task: Task) {
                    task.isDone = true
                    ToDoDataBase.getInstance(it).taskDao().updateTask(task)
                    Toast.makeText(it,"Done",Toast.LENGTH_SHORT).show()
                    adapter.notifyItemChanged(position)
                    adapter.notifyDataSetChanged()

                }
            }
            // edit task implementation
            adapter.onCardClick = object : TasksAdapter.OnButtonClickListener{
                override fun onClick(position: Int, task: Task) {
                    val intent = Intent(requireActivity(),EditActivity::class.java)
                    intent.putExtra("TITLE",task.taskTitle)
                    intent.putExtra("DESCRIPTION",task.taskDescription)
                    intent.putExtra("DATE", task.taskDate.toString())
                    intent.putExtra("STATE",task.isDone)
                    intent.putExtra("TASK",task)
                    startActivity(intent)
                }
            }

            // delete task implementation
            adapter.onDeleteClick = object : TasksAdapter.OnButtonClickListener{
                override fun onClick(position: Int, task: Task) {
                    ToDoDataBase.getInstance(it).taskDao().deleteTask(task)
                    Toast.makeText(it,"Task is Deleted Successfully",Toast.LENGTH_SHORT).show()
                    adapter.notifyItemRemoved(position)
                    adapter.notifyDataSetChanged()
                    loadTasks(date)
                }
            }

            // load tasks by date
            loadTasksByDate()

        }

    }

    private fun initView() {
        adapter = TasksAdapter(null, context)
        viewBinding.recyclerTasks.adapter = adapter
        viewBinding.calendarView.setSelectedDate(CalendarDay.today())
        if (calendar.timeInMillis!= null){
            viewBinding.calendarView.setSelectedDate(CalendarDay.today())
            loadTasks(calendar.timeInMillis)
        }else {
            viewBinding.calendarView.setSelectedDate(CalendarDay.today())
            selectToday()
            loadTasks(calendar.timeInMillis)
        }
    }

    fun selectToday() {
        calendar.set(Calendar.YEAR, CalendarDay.today().year)
        calendar.set(Calendar.MONTH, CalendarDay.today().month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, CalendarDay.today().day)
    }
}
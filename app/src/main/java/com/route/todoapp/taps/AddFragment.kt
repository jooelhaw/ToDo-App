package com.route.todoapp.taps

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todoapp.clearTime
import com.route.todoapp.databinding.FragmentAddBinding
import com.route.todoapp.tasksdb.Task
import com.route.todoapp.tasksdb.ToDoDataBase
import java.util.Calendar

class AddFragment: BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnAddTask.setOnClickListener {
            createTask()
        }
        viewBinding.taskDateLayout.setOnClickListener {
            showCalendar()
        }
    }

    val calendar = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showCalendar() {
        context?.let {
            val dialog = DatePickerDialog(it)
            dialog.setOnDateSetListener { datePicker, year, month, day ->
                viewBinding.taskDateInput.setText("$day-${month+1}-$year")
                calendar.set(Calendar.YEAR,year)
                calendar.set(Calendar.MONTH,month)
                calendar.set(Calendar.DAY_OF_MONTH,day)
                calendar.clearTime()
            }
            dialog.show()
        }

    }

    private fun createTask() {
        if (!validateTask())
        {
            return
        }
        val tast = Task(
            taskDate = calendar.timeInMillis,
            taskTitle = viewBinding.taskTitleInput.text.toString(),
            taskDescription = viewBinding.taskDescInput.text.toString())
        ToDoDataBase.getInstance(requireContext()).taskDao().insertTask(tast)
        onTaskAdded?.onAdded()
        dismiss()
    }
    var onTaskAdded: onAddedListener? = null
    fun interface onAddedListener{
        fun onAdded()
    }

    private fun validateTask() : Boolean {
        var isValid = true
        if (viewBinding.taskTitleInput.text.isNullOrBlank())
            {
                viewBinding.taskTitleLayout.error = "Please Enter the Title"
                isValid = false
            } else{
                viewBinding.taskTitleLayout.error = null
        }
        if (viewBinding.taskDescInput.text.isNullOrBlank())
        {
            viewBinding.taskDescLayout.error = "Please Enter the Description"
            isValid = false
        } else{
            viewBinding.taskDescLayout.error = null
        }
        if (viewBinding.taskDateInput.text.isNullOrBlank())
        {
            viewBinding.taskDateLayout.error = "Please Choose a Date"
            isValid = false
        } else{
            viewBinding.taskDateLayout.error = null
        }
        return isValid
    }
}
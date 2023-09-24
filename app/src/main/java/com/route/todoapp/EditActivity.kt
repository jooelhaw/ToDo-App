package com.route.todoapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.route.todoapp.databinding.ActivityEditBinding
import com.route.todoapp.tasksdb.Task
import com.route.todoapp.tasksdb.ToDoDataBase
import java.text.SimpleDateFormat
import java.util.Calendar

class EditActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityEditBinding
    lateinit var oldTaskTitle: String
    lateinit var oldTask: Task
    lateinit var oldTaskDescription: String
    lateinit var oldTaskDate: String
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var dateString:String
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        callOldData()
        //set update date to change task date
        viewBinding.oldDateLayout.setOnClickListener {
            showCalendar()
        }
        //set update button to update the task
        viewBinding.btnEditTask.setOnClickListener {
            updateTask()
        }


    }

    private fun updateTask() {
        if (!validateTask()){
            return
        }
        oldTask.taskDate = calendar.timeInMillis
        oldTask.taskTitle = viewBinding.oldTitleInput.text.toString()
        oldTask.taskDescription = viewBinding.oldDescInput.text.toString()

        ToDoDataBase.getInstance(this).taskDao().updateTask(oldTask)
        Snackbar.make(viewBinding.root,"Task Updated Successfully",Snackbar.LENGTH_SHORT).show()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    val calendar = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showCalendar() {
        val dialog = DatePickerDialog(this)
        dialog.setOnDateSetListener { datePicker, year, month, day ->
            viewBinding.oldDateInput.setText("$day-${month+1}-$year")
            calendar.set(year,month,day)
            calendar.clearTime()
        }
        dialog.show()
    }

    private fun callOldData() {
        oldTask = intent.getSerializableExtra("TASK") as Task
        oldTaskTitle = oldTask.taskTitle.toString()
        viewBinding.oldTitleInput.setText(oldTaskTitle)
        oldTaskDescription = oldTask.taskDescription.toString()
        viewBinding.oldDescInput.setText(oldTaskDescription)
        oldTaskDate = oldTask.taskDate.toString()
        simpleDateFormat = SimpleDateFormat("dd/MM/yy")
        dateString = simpleDateFormat.format(oldTaskDate.toLong())
        viewBinding.oldDateInput.setText(dateString)


    }
    private fun validateTask() : Boolean {
        var isValid = true
        if (viewBinding.oldTitleInput.text.isNullOrBlank())
        {
            viewBinding.oldTitleLayout.error = "Please Enter the Title"
            isValid = false
        } else{
            viewBinding.oldTitleLayout.error = null
        }
        if (viewBinding.oldDescInput.text.isNullOrBlank())
        {
            viewBinding.oldDescLayout.error = "Please Enter the Description"
            isValid = false
        } else{
            viewBinding.oldDescLayout.error = null
        }
        if (viewBinding.oldDateInput.text.isNullOrBlank())
        {
            viewBinding.oldDateLayout.error = "Please Choose a Date"
            isValid = false
        } else{
            viewBinding.oldDateLayout.error = null
        }
        return isValid
    }
}



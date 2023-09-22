package com.route.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.route.todoapp.databinding.ActivityEditBinding
import java.text.ParseException
import java.text.SimpleDateFormat

class EditActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityEditBinding
    lateinit var oldTaskTitle: String
    lateinit var oldTaskDescription: String
    lateinit var oldTaskDate: String
    lateinit var simpleDateFormat: SimpleDateFormat
    lateinit var dateString:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        callOldData()
        //set update button to update task by new data


    }

    private fun callOldData() {
        oldTaskTitle = intent.getStringExtra("TITLE").toString()
        viewBinding.oldTitleInput.setText(oldTaskTitle)
        oldTaskDescription = intent.getStringExtra("DESCRIPTION").toString()
        viewBinding.oldDescInput.setText(oldTaskDescription)
        oldTaskDate = intent.getStringExtra("DATE")!!
        simpleDateFormat = SimpleDateFormat("dd/MM/yy")
        dateString = simpleDateFormat.format(oldTaskDate.toLong())
        viewBinding.oldDateInput.setText(dateString)


    }
}



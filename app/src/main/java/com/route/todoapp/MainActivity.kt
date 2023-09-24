package com.route.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todoapp.databinding.ActivityMainBinding
import com.route.todoapp.taps.AddFragment
import com.route.todoapp.taps.SettingsFragment
import com.route.todoapp.taps.tasks.TasksFragment
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    lateinit var tasksFragmentRef: TasksFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings_btn -> showFragment(SettingsFragment())
                R.id.add_task -> showFragment(AddFragment())
                R.id.tasks_btn -> {
                    tasksFragmentRef = TasksFragment()
                    showFragment(tasksFragmentRef)
                }
            }
            return@setOnItemSelectedListener true
        }
        viewBinding.bottomNav.selectedItemId = R.id.tasks_btn
        viewBinding.addTask.setOnClickListener {
            showBottomSheet()
        }
    }

    val calendar = Calendar.getInstance()
    init {
        calendar.clearTime()
        calendar.set(Calendar.YEAR,CalendarDay.today().year)
        calendar.set(Calendar.MONTH,CalendarDay.today().month-1)
        calendar.set(Calendar.DAY_OF_MONTH,CalendarDay.today().day)
    }
    private fun showBottomSheet() {
        val addBottomSheet = AddFragment()
        addBottomSheet.onTaskAdded = AddFragment.onAddedListener {
            Snackbar.make(viewBinding.root,"Task Added Successfully",Snackbar.LENGTH_SHORT).show()
            var titm:Long = tasksFragmentRef.calendar.timeInMillis
            tasksFragmentRef.loadTasks(titm)
        }
        addBottomSheet.show(supportFragmentManager,"")
    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }
}
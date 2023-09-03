package com.route.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoapp.databinding.ActivityMainBinding
import com.route.todoapp.databinding.HomeContentBinding
import com.route.todoapp.taps.AddFragment
import com.route.todoapp.taps.SettingsFragment
import com.route.todoapp.taps.TasksFragment

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings_btn -> showFragment(SettingsFragment())
                R.id.add_task -> showFragment(AddFragment())
                R.id.tasks_btn -> showFragment(TasksFragment())
            }
            return@setOnItemSelectedListener true
        }
        viewBinding.bottomNav.selectedItemId = R.id.tasks_btn
        viewBinding.addTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val addBottomSheet = AddFragment()
        addBottomSheet.show(supportFragmentManager,"")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()

    }
}
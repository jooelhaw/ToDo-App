package com.route.todoapp.taps.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todoapp.databinding.ItemTasksBinding
import com.route.todoapp.tasksdb.Task

class TasksAdapter(var tasks: List<Task>?) : Adapter<TasksAdapter.TasksViewHoler>() {
    class TasksViewHoler(val itemBinding: ItemTasksBinding) : ViewHolder(itemBinding.root){
        fun bind(task: Task) {
            itemBinding.rvTaskTitle.text = task.taskTitle.toString()
            itemBinding.rvTaskDesc.text = task.taskDescription.toString()
            //itemBinding.rvTaskDate.text = task.taskDate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHoler {
        val itemBinding = ItemTasksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TasksViewHoler(itemBinding)
    }

    override fun getItemCount(): Int = tasks?.size?:0

    override fun onBindViewHolder(holder: TasksViewHoler, position: Int) {
        holder.bind(tasks!![position])
    }

    fun bindTask(tasksList: List<Task>) {
        this.tasks = tasksList
        notifyDataSetChanged()
    }


}
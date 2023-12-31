package com.route.todoapp.taps.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.route.todoapp.R
import com.route.todoapp.databinding.ItemTasksBinding
import com.route.todoapp.tasksdb.Task
import java.text.SimpleDateFormat

class TasksAdapter(var tasks: List<Task>?,val context: Context?) : Adapter<TasksAdapter.TasksViewHoler>() {
    class TasksViewHoler(val itemBinding: ItemTasksBinding) : ViewHolder(itemBinding.root){
        fun bind(task: Task) {
            itemBinding.rvTaskTitle.text = task.taskTitle.toString()
            itemBinding.rvTaskDesc.text = task.taskDescription.toString()
            val simplaDateFormat = SimpleDateFormat("dd/MM/yy")
            val dateString = simplaDateFormat.format(task.taskDate)
            itemBinding.rvTaskDate.text = dateString.toString()
        }
    }
    var onButtonClick: OnButtonClickListener? = null
    var onCardClick: OnButtonClickListener? = null
    var onDeleteClick: OnButtonClickListener? = null
    fun interface OnButtonClickListener{
        fun onClick(position: Int, task: Task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHoler {
        val itemBinding = ItemTasksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TasksViewHoler(itemBinding)
    }

    override fun getItemCount(): Int = tasks?.size?:0

    override fun onBindViewHolder(holder: TasksViewHoler, position: Int) {
        holder.bind(tasks!![position])
        if (onButtonClick!= null) {
            holder.itemBinding.rvTaskDonebtn.setOnClickListener {
                onButtonClick?.onClick(position, tasks!![position])
            }
        }

        // edit task
        if (onCardClick!= null) {
            holder.itemBinding.cardTask.setOnLongClickListener {
                onCardClick?.onClick(position,tasks!![position])
                true
            }
        }

        // is task made done
        isTaskDone(holder,position)

        // delete task
        if (onDeleteClick!= null){
            holder.itemBinding.deleteBtn.setOnClickListener {
                onDeleteClick?.onClick(position,tasks!![position])
            }
        }
    }

    private fun isTaskDone(holder: TasksViewHoler, position: Int) {
        if(tasks!![position].isDone == true) {
            holder.itemBinding.rvTaskTitle.setTextColor(
                getColor(context!!,R.color.done_task)
            )
            holder.itemBinding.rvTaskDonebtn.setImageResource(R.drawable.ic_done_foreground)
            holder.itemBinding.rvTaskDonebtn.setBackgroundColor(getColor(context,R.color.ic_done_background))
            holder.itemBinding.rvtaskState.background = getDrawable(context,R.drawable.state_done_shape)
            holder.itemBinding.rvTaskDonebtn.isClickable = false
        }
        else{
            holder.itemBinding.rvTaskTitle.setTextColor(getColor(context!!,R.color.app_bar_color))
            holder.itemBinding.rvTaskDonebtn.setImageResource(R.drawable.ic_done)
            holder.itemBinding.rvTaskDonebtn.background = getDrawable(context,R.drawable.done_btn_shape)
            holder.itemBinding.rvtaskState.background = getDrawable(context,R.drawable.state_shape)

        }
    }

    fun bindTask(tasksList: List<Task>) {
        this.tasks = tasksList
        notifyDataSetChanged()
    }


}
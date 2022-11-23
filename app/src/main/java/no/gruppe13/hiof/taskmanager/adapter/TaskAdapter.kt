package no.gruppe13.hiof.taskmanager.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.databinding.TaskListItemBinding

class TaskAdapter() : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback) {

    private var position = 0

    class TaskViewHolder(private var binding: TaskListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskTitle.text = task.title
            binding.checkBoxItem.isChecked = task.completed // Sjekk i Task om skal være false default
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val viewHolder = TaskViewHolder(
            TaskListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    // For å streke gjennom item
    private fun toggleStrikeThrough(taskTitle: TextView, completed: Boolean) {
         if (completed) {
             taskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
         } else {
             taskTitle.paintFlags = taskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
         }
     }

    //val checkbox = view.findViewById<CheckBox>(R.id.checkBoxItem)

    //Wrap en funksjon rundt denne
    /* toggleStrikeThrough(taskTitle, currentTask.completed)
     checkbox.setOnCheckedChangeListener { _, _ ->
         toggleStrikeThrough(taskTitle, completed)
         currentTask.completed = !currentTask.completed
     }*/

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)




/*
       toggleStrikeThrough(tvTaskTitle, currentTask.completed)
        checkbox.setOnCheckedChangeListener { _, _ ->
            toggleStrikeThrough(tvTaskTitle, completed)
            currentTask.completed = !currentTask.completed
        }*/
    }

 /*   override fun getItemCount(): Int {
    return taskItems.size    }*/

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }
}
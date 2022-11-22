package no.gruppe13.hiof.taskmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.databinding.TaskListItemBinding

class TaskAdapter (private val taskItems: MutableList<Task>) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback) {

    class TaskViewHolder(private var binding: TaskListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.tvTaskTitle.text = task.title
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
        //viewHolder.itemView.

        //viewHolder.itemView.t
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val currentTask = taskItems[position]
        holder.bind(currentTask)

    }

    override fun getItemCount(): Int {
    return taskItems.size    }

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
package no.gruppe13.hiof.taskmanager.adapter

/*
 - The code for managing database and files that tie it together are greatly inspired by Android's tutorial:
https://developer.android.com/courses/pathways/android-basics-kotlin-unit-5-pathway-1#codelab-https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow

- The code for handling taskitems, especially the toggleStrikeThrough function, is highly influenced by this video on youtube:
https://www.youtube.com/watch?v=BBWyXo-3JGQ&list=PLyLg4QPpmOhC5d92CtHAbiIaHGoRGOO_C&index=4&t=1882s
*/

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.databinding.TaskListItemBinding

class TaskAdapter() : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallback) {

    class TaskViewHolder(private var binding: TaskListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskTitle.text = task.title
            binding.checkBoxItem.isChecked = task.completed
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

    private fun toggleStrikeThrough(taskTitle: TextView, completed: Boolean) {
        if (completed) {
            taskTitle.paintFlags = taskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            taskTitle.paintFlags = taskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        val title: TextView = holder.itemView.findViewById(R.id.taskTitle)
        val checked: CheckBox = holder.itemView.findViewById(R.id.checkBoxItem)

        title.text = item.title
        checked.isChecked = item.completed

        toggleStrikeThrough(title, checked.isChecked)
        checked.setOnCheckedChangeListener { _, _ ->
            toggleStrikeThrough(title, checked.isChecked)
            item.completed = !item.completed
        }
    }


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
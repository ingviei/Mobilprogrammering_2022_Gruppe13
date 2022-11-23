package no.gruppe13.hiof.taskmanager.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.databinding.TaskListItemBinding
import org.w3c.dom.Text

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
            taskTitle.paintFlags = taskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
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
        val title: TextView = holder.itemView.findViewById(R.id.taskTitle)
        val checked: CheckBox = holder.itemView.findViewById(R.id.checkBoxItem)

        title.text = item.title
        checked.isChecked = item.completed

        toggleStrikeThrough(title, checked.isChecked)
        checked.setOnCheckedChangeListener { _, _ ->
            toggleStrikeThrough(title, checked.isChecked)
            item.completed = !item.completed


        }
/*        tvTodoTitle.text = curTodo.title
        cbDone.isChecked = curTodo.isChecked
        toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
        cbDone.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(tvTodoTitle, isChecked)
            curTodo.isChecked = !curTodo.isChecked
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
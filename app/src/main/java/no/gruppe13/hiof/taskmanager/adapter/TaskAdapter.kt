package no.gruppe13.hiof.taskmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.data.Task

class TaskAdapter (private val taskItems: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val viewHolder = TaskViewHolder(
            LayoutInflater.from(parent.context), parent, false)}

    override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
        val currentTask = taskItems[position]
        holder.itemView.apply {
            holder.itemView.tv_taskTitle
        }
    }

    override fun getItemCount(): Int {
    return taskItems.size    }
}
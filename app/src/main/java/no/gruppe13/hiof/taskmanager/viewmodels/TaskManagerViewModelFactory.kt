package no.gruppe13.hiof.taskmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import no.gruppe13.hiof.taskmanager.TaskManagerApplication
import no.gruppe13.hiof.taskmanager.data.TaskDao
import no.gruppe13.hiof.taskmanager.data.category.CategoryDao

class TaskManagerViewModelFactory(
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(
                TaskManagerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskManagerViewModel(taskDao, categoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
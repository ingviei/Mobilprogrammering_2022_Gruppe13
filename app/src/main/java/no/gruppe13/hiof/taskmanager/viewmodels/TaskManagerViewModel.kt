package no.gruppe13.hiof.taskmanager.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import no.gruppe13.hiof.taskmanager.data.task.Task
import no.gruppe13.hiof.taskmanager.data.category.CategoryDao
import no.gruppe13.hiof.taskmanager.data.task.TaskDao
import no.gruppe13.hiof.taskmanager.data.category.Category
import java.util.*

class TaskManagerViewModel(private val taskDao: TaskDao, private val categoryDao: CategoryDao): ViewModel() {
    fun allTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    fun getFilteredTasks(dateFrom: String?, dateTo: String?, categoryId: Int): Flow<List<Task>> {
        if (!(dateFrom.isNullOrEmpty() || dateTo.isNullOrEmpty())) {
            return taskDao.getTasksBetweenDates(dateFrom, dateTo)
        } else if (!dateFrom.isNullOrEmpty()) {
            return taskDao.getTasksFromDate(dateFrom)
        } else if (!dateTo.isNullOrEmpty()) {
            return taskDao.getTasksToDate(dateTo)
        } else if (categoryId >= 0) {
            return taskDao.getTasksByCategoryId(categoryId)
        } else { // No filter
            return taskDao.getAllTasks()
        }
    }
    fun allCategories(): Flow<List<Category>> = categoryDao.getAll()
    fun getCategory(categoryId: Int) = categoryDao.getCategory(categoryId)
    fun deleteCompleted() = taskDao.deleteCompleted()

    //fun getTask(taskId: Int) = taskDao.getTask(taskId)

    val selectedDateTime: MutableLiveData<Date> = MutableLiveData()
}
package no.gruppe13.hiof.taskmanager.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import no.gruppe13.hiof.taskmanager.data.task.Task
import no.gruppe13.hiof.taskmanager.data.task.TaskDao

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    //fun getTasks() = taskDao.getAllTasks()
    //fun getTask(taskUid: Int) = taskDao.getTask(taskUid)


//    Vurdere om denne må med eller ikke
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }
}
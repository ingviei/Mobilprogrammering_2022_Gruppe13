package no.gruppe13.hiof.taskmanager.data

class TaskRepository(private val taskDao: TaskDao) {
    fun getTasks() = taskDao.getAllTasks()
    fun getTask(taskUid: Int) = taskDao.getTask(taskUid)
}
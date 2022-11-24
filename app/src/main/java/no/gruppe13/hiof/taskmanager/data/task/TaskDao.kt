package no.gruppe13.hiof.taskmanager.data.task

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import no.gruppe13.hiof.taskmanager.data.task.Task

// https://developer.android.com/training/data-storage/room/async-queries
// https://developer.android.com/reference/kotlin/androidx/room/Insert
// https://developer.android.com/training/data-storage/room/accessing-data


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)
    // suspend is for making the database asynchronous, One shot queries
    // Legge på vararg?

    //Bruke update og delete på denne måten? se annen delete metode lenger ned

    @Update
    suspend fun updateTask(task: Task)

    @Query("DELETE FROM task WHERE completed = 1")
    fun deleteCompleted()

    @Query("SELECT * FROM task ORDER BY date ASC, time ASC")
    fun getAllTasks(): Flow<List<Task>>
//    Bytte ut med fun getAllTasks(): Array<Task> eller List<Task> ??? Ref. https://developer.android.com/training/data-storage/room/accessing-data

    @Query("SELECT * FROM task WHERE date >= :fromDate ORDER BY date ASC, time ASC")
    fun getTasksFromDate(fromDate: String): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE date <= :toDate ORDER BY date ASC, time ASC")
    fun getTasksToDate(toDate: String): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE date BETWEEN :fromDate AND :toDate ORDER BY date ASC, time ASC")
    fun getTasksBetweenDates(fromDate: String, toDate: String): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE category_id = :categoryId ORDER BY date ASC, time ASC")
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun getTask(taskId: Int): Flow<Task>

    @Query("DELETE FROM task")
    suspend fun deleteAll()


}
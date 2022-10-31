package no.gruppe13.hiof.taskmanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// https://developer.android.com/training/data-storage/room/async-queries
// https://developer.android.com/reference/kotlin/androidx/room/Insert
// https://developer.android.com/training/data-storage/room/accessing-data


@Dao
interface TaskDao {
    @Insert //legge til onConflictStrategy?
    suspend fun insertTask(task: Task)
    // suspend is for making the database asynchronous, One shot queries
    // Legge på vararg?

    //Bruke update og delete på denne måten? se annen delete metode lenger ned
    /*@Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)*/

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>
//    Bytte ut med fun getAllTasks(): Array<Task> ??? Ref. https://developer.android.com/training/data-storage/room/accessing-data

    @Query("SELECT * FROM task WHERE uid = :taskUid") // Bytte ut med bare uid?
    fun getTask(taskUid: Int): Flow<Task>

    @Query("DELETE FROM task")
    suspend fun deleteAll()


}
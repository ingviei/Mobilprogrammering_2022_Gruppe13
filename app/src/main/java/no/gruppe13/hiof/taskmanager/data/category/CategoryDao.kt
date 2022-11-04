package no.gruppe13.hiof.taskmanager.data.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import no.gruppe13.hiof.taskmanager.data.Task

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM task_category ORDER BY title ASC")
    // ToDo: Decide if should be mutable
    fun getAll(): Flow<List<Category>>

}

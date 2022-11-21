package no.gruppe13.hiof.taskmanager.data.category

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import no.gruppe13.hiof.taskmanager.data.Task

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM task_category ORDER BY title ASC")
    // ToDo: Decide if should be mutable
    fun getAll(): Flow<List<Category>>

    @Query("SELECT * FROM task_category WHERE id = :categoryId")
    fun getCategory(categoryId: Int): Flow<Category>

    @Update
    suspend fun updateCategory(category: Category)

    @Query("DELETE FROM task_category WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Int)

    @Query("DELETE FROM task_category")
    suspend fun deleteAll()
}

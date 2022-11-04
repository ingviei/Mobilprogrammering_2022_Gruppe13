package no.gruppe13.hiof.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.data.category.CategoryDao

@Database(entities = [Category:: class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase (){
    //abstract fun taskDao() : TaskDao
    abstract fun categoryDao() : CategoryDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}
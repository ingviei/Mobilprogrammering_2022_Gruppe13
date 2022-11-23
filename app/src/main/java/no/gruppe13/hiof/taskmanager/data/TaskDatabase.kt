package no.gruppe13.hiof.taskmanager.data

/*The code for implementing the database and the belonging classes is influenced by this codelab:
https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0

... and also code from the lectures in this subject 'Mobilprogrammering' at Høgskolen in Østfold*/

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.data.category.CategoryDao
import no.gruppe13.hiof.taskmanager.data.converters.Converters

@Database(entities = [Category:: class, Task:: class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase (){
    abstract fun taskDao() : TaskDao
    abstract fun categoryDao() : CategoryDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database")
                    .createFromAsset("database/task_database.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
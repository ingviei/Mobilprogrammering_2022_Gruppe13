package no.gruppe13.hiof.taskmanager.data.category

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "task_category")
data class Category (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @NonNull
    var title : String,
    var description : String
)
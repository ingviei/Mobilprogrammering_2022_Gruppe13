package no.gruppe13.hiof.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "task")
data class Task (var title : String, var category: String, var date : String, var time : String, var comment : String) {

    @PrimaryKey(autoGenerate = true)
    var uid : Int = 0

}
package no.gruppe13.hiof.taskmanager.data.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey
import no.gruppe13.hiof.taskmanager.data.category.Category
import java.util.*

@Entity(tableName = "task",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        childColumns = ["category_id"],
        parentColumns = ["id"],
        onDelete = SET_NULL
    )])
data class Task (
    var title : String,
    //var category: Category,
    @ColumnInfo(name = "category_id") val categoryId: Long?,
    var date : String,
    var time : String,
    //var dateTime : Date?,
    var comment : String,
    var completed : Boolean
    //var completed : Boolean = false
) {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

}
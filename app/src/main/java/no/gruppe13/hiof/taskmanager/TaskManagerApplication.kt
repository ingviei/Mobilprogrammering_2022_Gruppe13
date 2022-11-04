package no.gruppe13.hiof.taskmanager

import android.app.Application
import no.gruppe13.hiof.taskmanager.data.TaskDatabase

class TaskManagerApplication : Application() {
    val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this)}
}
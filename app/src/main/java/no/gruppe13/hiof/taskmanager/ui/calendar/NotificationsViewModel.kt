package no.gruppe13.hiof.taskmanager.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Calendar Fragment"
    }
    val text: LiveData<String> = _text
}
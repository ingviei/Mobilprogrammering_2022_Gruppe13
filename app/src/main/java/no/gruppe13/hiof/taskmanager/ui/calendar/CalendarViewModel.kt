package no.gruppe13.hiof.taskmanager.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kalender-fragment"
    }
    val text: LiveData<String> = _text
}
package no.gruppe13.hiof.taskmanager

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.timepicker.TimeFormat
import no.gruppe13.hiof.taskmanager.DatePickerFragment.Companion.FORMATTED_DATE_KEY
import no.gruppe13.hiof.taskmanager.DatePickerFragment.Companion.REQUEST_KEY
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalAccessor
import java.util.*

/**
 * This code is heavily influenced by example code from developer.android.com
 * (see: https://developer.android.com/develop/ui/views/components/pickers#kotlin )
 */
class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private val c = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)

        val formattedTime = String.format("%02d:%02d", hourOfDay, minute)

        setFragmentResult(TimePickerFragment.REQUEST_KEY, bundleOf(TimePickerFragment.FORMATTED_TIME_KEY to formattedTime))
    }

    companion object {
        const val REQUEST_KEY = "task-timepicker-request"
        const val FORMATTED_TIME_KEY = "formatted-time"
    }
}
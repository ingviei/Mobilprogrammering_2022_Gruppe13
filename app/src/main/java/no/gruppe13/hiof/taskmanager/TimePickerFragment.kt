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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TimePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
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

        //val tf = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault())
        //val ldt = c.time.toInstant().atOffset(ZoneOffset.of(ZoneOffset.systemDefault().toString())).toLocalDateTime()
        //val formattedTime = ldt.format(tf)
//        val df: java.text.DateFormat = java.text.DateFormat.getDateInstance(java.text.DateFormat., Locale.getDefault())
//        val formattedDate: String = df.format(c.time)
        setFragmentResult(TimePickerFragment.REQUEST_KEY, bundleOf(TimePickerFragment.FORMATTED_TIME_KEY to formattedTime))
    }

    companion object {
        const val REQUEST_KEY = "task-timepicker-request"
        const val FORMATTED_TIME_KEY = "formatted-time"
    }
    /*
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_picker, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TimePickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TimePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}
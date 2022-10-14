package no.gruppe13.hiof.taskmanager

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import java.text.DateFormat
import java.util.*

/**
 * This code is heavily influenced by example code from developer.android.com
 * (see: https://developer.android.com/develop/ui/views/components/pickers#kotlin )
 */
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val c = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        c.set(year, month, day)
        val df: DateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD, Locale.getDefault())
        val formattedDate: String = df.format(c.time)
        setFragmentResult(REQUEST_KEY, bundleOf(FORMATTED_DATE_KEY to formattedDate))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    companion object {
        const val REQUEST_KEY = "task-datepicker-request"
        const val FORMATTED_DATE_KEY = "formatted-date"
    }
}
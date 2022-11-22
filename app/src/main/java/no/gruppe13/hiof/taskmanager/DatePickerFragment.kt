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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
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
        val dateString: String = String.format("%04d-%02d-%02d", year, month, day);

        setNavigationResult("picked_date", dateString)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    // Extension function lånt fra https://stackoverflow.com/questions/56624895/android-jetpack-navigation-component-result-from-dialog
    fun <T>Fragment.setNavigationResult(key: String, value: T) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            key,
            value
        )
    }


    companion object {
        const val REQUEST_KEY = "task-datepicker-request"
        const val FORMATTED_DATE_KEY = "formatted-date"
    }
}
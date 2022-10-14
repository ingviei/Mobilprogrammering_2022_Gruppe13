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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DatePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
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
        //val result = c.set(year, month, day)
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

    /*// TODO: Rename and change types of parameters
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
        return inflater.inflate(R.layout.fragment_date_picker, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DatePickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DatePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}
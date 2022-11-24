package no.gruppe13.hiof.taskmanager.ui.calendar

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.NumberPicker.OnValueChangeListener
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.DatePickerFragment
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.databinding.FragmentCalendarBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields
import java.time.temporal.WeekFields
import java.util.*


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    private lateinit var weekArrow: ImageButton
    private lateinit var weekHiddenView: ConstraintLayout
    private lateinit var weekCardView: CardView
    private lateinit var monthArrow: ImageButton
    private lateinit var monthHiddenView: ConstraintLayout
    private lateinit var monthCardView: CardView
    private lateinit var intervalArrow: ImageButton
    private lateinit var intervalHiddenView: ConstraintLayout
    private lateinit var intervalCardView: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController()

        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    findNavController().navigate(R.id.navigation_home)
                    true
                }
                R.id.nav_calendar -> {
                    findNavController().navigate(R.id.navigation_calendar)
                    true
                }
                else -> false
            }
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            weekCardView = view.findViewById(R.id.week_calendar_card_view)
            weekArrow = view.findViewById(R.id.week_arrow_button)
            weekHiddenView = view.findViewById(R.id.week_hidden_view)
            monthCardView = view.findViewById(R.id.month_calendar_card_view)
            monthArrow = view.findViewById(R.id.month_arrow_button)
            monthHiddenView = view.findViewById(R.id.month_hidden_view)
            intervalCardView = view.findViewById(R.id.interval_calendar_card_view)
            intervalArrow = view.findViewById(R.id.interval_arrow_button)
            intervalHiddenView = view.findViewById(R.id.interval_hidden_view)
        } catch(e: Exception) {

        }

        setVisibleArea(-1) // Hide all sections if open

        weekArrow.setOnClickListener { view: View? ->
            setVisibleArea(0)
        }
        monthArrow.setOnClickListener { view: View? ->
            setVisibleArea(1)
        }
        intervalArrow.setOnClickListener { view: View? ->
            setVisibleArea(2)
        }

        val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        val weekYearPicker = requireActivity().findViewById(R.id.week_layout_year_picker) as NumberPicker
        weekYearPicker.minValue = currentYear - 1
        weekYearPicker.maxValue = currentYear + 6
        weekYearPicker.value = currentYear

        val firstWeekOfYear = 1
        val currentWeek = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val weekOfLastDate = LocalDate.of(currentYear, 12, 31).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val weekOfPrelastWeek = LocalDate.of(currentYear, 12, 24).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        var lastWeekOfYear: Int
        // Find out if week of 31/12 has higher week number than the week before
        if (weekOfLastDate > weekOfPrelastWeek) {
            lastWeekOfYear = weekOfLastDate
        } else {
            lastWeekOfYear = weekOfPrelastWeek
        }

        val weekWeekPicker = requireActivity().findViewById(R.id.week_layout_week_picker) as NumberPicker
        weekWeekPicker.minValue = firstWeekOfYear
        weekWeekPicker.maxValue = lastWeekOfYear
        weekWeekPicker.value = currentWeek

        // Set the last week of year when different year selected
        weekYearPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            val currentYear = newVal
            val weekOfLastDate = LocalDate.of(currentYear, 12, 31).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
            val weekOfPrelastWeek = LocalDate.of(currentYear, 12, 24).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
            var lastWeekOfYear: Int
            // Find out if week of 31/12 has higher week number than the week before
            if (weekOfLastDate > weekOfPrelastWeek) {
                lastWeekOfYear = weekOfLastDate
            } else {
                lastWeekOfYear = weekOfPrelastWeek
            }
            val weekWeekPicker = requireActivity().findViewById(R.id.week_layout_week_picker) as NumberPicker
            weekWeekPicker.maxValue = lastWeekOfYear
        })

        val monthYearPicker = requireActivity().findViewById(R.id.month_layout_year_picker) as NumberPicker
        monthYearPicker.minValue = currentYear - 1
        monthYearPicker.maxValue = currentYear + 6
        monthYearPicker.value = currentYear

        val monthMonthPicker = requireActivity().findViewById(R.id.month_layout_month_picker) as NumberPicker
        monthMonthPicker.minValue = 1
        monthMonthPicker.maxValue = 12
        monthMonthPicker.value = LocalDate.now().monthValue


        requireActivity().findViewById<ImageButton>(R.id.calendar_from_button).setOnClickListener() {
            showDatePicker(it, "FROM_DATE")
        }

        requireActivity().findViewById<ImageButton>(R.id.calendar_to_button).setOnClickListener() {
            showDatePicker(it, "TO_DATE")
        }

        getNavigationResult<String>(
            Navigation.findNavController(this.requireView()).currentDestination!!.id,
            "FROM_DATE") {
            setDateFromText(it)
        }

        getNavigationResult<String>(
            Navigation.findNavController(this.requireView()).currentDestination!!.id,
            "TO_DATE") { setDateToText(it) }

        val viewTasksButton = binding.viewTasksButton

        viewTasksButton.setOnClickListener() {
            viewTasks(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun viewTasks(view: View) {
        if (weekHiddenView.visibility == View.VISIBLE) {
            val week: Long = requireActivity().findViewById<NumberPicker>(R.id.week_layout_week_picker).value.toLong()
            val year: Long = requireActivity().findViewById<NumberPicker>(R.id.week_layout_year_picker).value.toLong()
            // Calculate first date of the week
            val fromDate = LocalDate.now()
                .with(WeekFields.ISO.weekBasedYear(), year)
                .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
                .with(WeekFields.ISO.dayOfWeek(), DayOfWeek.MONDAY.getValue().toLong());

            // Calculate last date of the week
            val toDate = fromDate.plusDays(6)
            val header = "UKE " + week + " - " + year
            val categoryId = -1
            val action = CalendarFragmentDirections.actionNavigationCalendarToNavigationTasks(fromDate.format(
                DateTimeFormatter.ISO_DATE), toDate.format(DateTimeFormatter.ISO_DATE), categoryId, header)
            findNavController().navigate(action)
        } else if (monthHiddenView.visibility == View.VISIBLE) {
            val month: Int = requireActivity().findViewById<NumberPicker>(R.id.month_layout_month_picker).value
            val year: Int = requireActivity().findViewById<NumberPicker>(R.id.month_layout_year_picker).value

            // Calculate first date of the week
            val fromMonth = YearMonth.from(LocalDate.now().withYear(year).withMonth(month).withDayOfMonth(1))
            val fromDate = fromMonth.atDay(1)

            // Calculate last date of the week
            val toDate = fromMonth.atEndOfMonth()
            val header = "MÅNED " + year + "-" + month
            val categoryId = -1
            val action = CalendarFragmentDirections.actionNavigationCalendarToNavigationTasks(fromDate.format(
                DateTimeFormatter.ISO_DATE), toDate.format(DateTimeFormatter.ISO_DATE), categoryId, header)
            findNavController().navigate(action)

        } else if (intervalHiddenView.visibility == View.VISIBLE) {
            val fromDate = requireActivity().findViewById<EditText>(R.id.pick_from_interval_input).text.toString()
            val toDate = requireActivity().findViewById<EditText>(R.id.pick_to_interval_input).text.toString()

            val header = "OPPGAVER I PERIODEN"
            val categoryId = -1
            val action = CalendarFragmentDirections.actionNavigationCalendarToNavigationTasks(fromDate, toDate, categoryId, header)
            findNavController().navigate(action)
        }
    }

    private fun setDateFromText(dateString: String) {
        requireActivity().findViewById<EditText>(R.id.pick_from_interval_input).setText(dateString)
    }

    private fun setDateToText(dateString: String) {
        requireActivity().findViewById<EditText>(R.id.pick_to_interval_input).setText(dateString)
    }

    private fun showDatePicker(v: View, returnKey: String = "picked_date") {
        val action = CalendarFragmentDirections.actionCalendarFragmentToNavigationDatePicker(returnKey)
        findNavController().navigate(action)
    }

    // Kode tilpasset fra https://www.geeksforgeeks.org/how-to-create-an-expandable-cardview-in-android/
    // For each area (week, month or interval) hide or display details
    private fun setVisibleArea(areaIndex: Int) {
        var areaArrows: Array<ImageButton> = arrayOf(weekArrow, monthArrow, intervalArrow)
        var areaHiddenViews: Array<ConstraintLayout> = arrayOf(weekHiddenView, monthHiddenView, intervalHiddenView)
        var areaCardViews: Array<CardView> = arrayOf(weekCardView, monthCardView, intervalCardView)

        for (i: Int in 0 .. 2) {
            if (i == areaIndex && areaHiddenViews[i].visibility != View.VISIBLE) { // Found view to show
                    TransitionManager.beginDelayedTransition(areaCardViews[i], AutoTransition())
                    areaHiddenViews[i].visibility = View.VISIBLE
                    areaArrows[i].setImageResource(R.drawable.ic_baseline_expand_less_24)
            } else if (areaHiddenViews[i].visibility != View.GONE) { // View is visible but should be gone
                TransitionManager.beginDelayedTransition(areaCardViews[i], AutoTransition())
                areaHiddenViews[i].visibility = View.GONE
                areaArrows[i].setImageResource(R.drawable.ic_baseline_expand_more_24)
            }
        }
    }

    // Extension function lånt fra https://stackoverflow.com/questions/56624895/android-jetpack-navigation-component-result-from-dialog
    private fun <T>Fragment.getNavigationResult(@IdRes id: Int, key: String, onResult: (result: T) -> Unit) {
        val navBackStackEntry = findNavController().getBackStackEntry(id)

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME
                && navBackStackEntry.savedStateHandle.contains(key)
            ) {
                val result = navBackStackEntry.savedStateHandle.get<T>(key)
                result?.let(onResult)
                navBackStackEntry.savedStateHandle.remove<T>(key)
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }
}
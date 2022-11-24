package no.gruppe13.hiof.taskmanager.ui.calendar

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import no.gruppe13.hiof.taskmanager.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.temporal.IsoFields
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
        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController()

        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                no.gruppe13.hiof.taskmanager.R.id.nav_home -> {
                    findNavController().navigate(no.gruppe13.hiof.taskmanager.R.id.navigation_home)
                    true
                }
                no.gruppe13.hiof.taskmanager.R.id.nav_calendar -> {
                    findNavController().navigate(no.gruppe13.hiof.taskmanager.R.id.navigation_calendar)
                    true
                }
                else -> false
            }
        }

        requireActivity().supportFragmentManager
            .setFragmentResultListener(DatePickerFragment.REQUEST_KEY, this) { requestKey, bundle ->
                val result = bundle.getString(DatePickerFragment.FORMATTED_DATE_KEY)
                requireActivity().findViewById<EditText>(no.gruppe13.hiof.taskmanager.R.id.pick_from_interval_input).setText(result)
            }

        requireActivity().supportFragmentManager
            .setFragmentResultListener(DatePickerFragment.REQUEST_KEY, this) { requestKey, bundle ->
                val result = bundle.getString(DatePickerFragment.FORMATTED_DATE_KEY)
                requireActivity().findViewById<EditText>(no.gruppe13.hiof.taskmanager.R.id.pick_to_interval_input).setText(result)
            }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            weekCardView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.week_calendar_card_view)
            weekArrow = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.week_arrow_button)
            weekHiddenView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.week_hidden_view)
            monthCardView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.month_calendar_card_view)
            monthArrow = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.month_arrow_button)
            monthHiddenView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.month_hidden_view)
            intervalCardView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.interval_calendar_card_view)
            intervalArrow = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.interval_arrow_button)
            intervalHiddenView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.interval_hidden_view)
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
        val weekYearPicker = requireActivity().findViewById(no.gruppe13.hiof.taskmanager.R.id.week_layout_year_picker) as NumberPicker
        weekYearPicker.minValue = currentYear - 1
        weekYearPicker.maxValue = currentYear + 6
        weekYearPicker.value = currentYear

        val firstWeekOfYear = 1
        val calendar: Calendar = Calendar.getInstance()
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

        val weekWeekPicker = requireActivity().findViewById(no.gruppe13.hiof.taskmanager.R.id.week_layout_week_picker) as NumberPicker
        weekWeekPicker.minValue = firstWeekOfYear
        weekWeekPicker.maxValue = lastWeekOfYear
        weekWeekPicker.value = currentWeek

        val monthYearPicker = requireActivity().findViewById(no.gruppe13.hiof.taskmanager.R.id.month_layout_year_picker) as NumberPicker
        monthYearPicker.minValue = currentYear - 1
        monthYearPicker.maxValue = currentYear + 6
        monthYearPicker.value = currentYear

        val monthMonthPicker = requireActivity().findViewById(no.gruppe13.hiof.taskmanager.R.id.month_layout_month_picker) as NumberPicker
        monthMonthPicker.minValue = 1
        monthMonthPicker.maxValue = 12
        monthMonthPicker.value = LocalDate.now().monthValue


        requireActivity().findViewById<ImageButton>(no.gruppe13.hiof.taskmanager.R.id.calendar_from_button).setOnClickListener() {
            showDatePicker(it, "FROM_DATE")
        }

        requireActivity().findViewById<ImageButton>(no.gruppe13.hiof.taskmanager.R.id.calendar_to_button).setOnClickListener() {
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
    }

    fun setDateFromText(dateString: String) {
        requireActivity().findViewById<EditText>(no.gruppe13.hiof.taskmanager.R.id.pick_from_interval_input).setText(dateString)
    }

    fun setDateToText(dateString: String) {
        requireActivity().findViewById<EditText>(no.gruppe13.hiof.taskmanager.R.id.pick_to_interval_input).setText(dateString)
    }

    fun showDatePicker(v: View, returnKey: String = "picked_date") {
        val action = CalendarFragmentDirections.actionCalendarFragmentToNavigationDatePicker(returnKey)
        findNavController().navigate(action)
    }

    // Kode tilpasset fra https://www.geeksforgeeks.org/how-to-create-an-expandable-cardview-in-android/
    // For each area (week, month or interval) hide or display details
    fun setVisibleArea(areaIndex: Int) {
        var areaArrows: Array<ImageButton> = arrayOf(weekArrow, monthArrow, intervalArrow)
        var areaHiddenViews: Array<ConstraintLayout> = arrayOf(weekHiddenView, monthHiddenView, intervalHiddenView)
        var areaCardViews: Array<CardView> = arrayOf(weekCardView, monthCardView, intervalCardView)

        for (i: Int in 0 .. 2) {
            if (i == areaIndex && areaHiddenViews[i].visibility != View.VISIBLE) { // Found view to show
                    TransitionManager.beginDelayedTransition(areaCardViews[i], AutoTransition())
                    areaHiddenViews[i].visibility = View.VISIBLE
                    areaArrows[i].setImageResource(no.gruppe13.hiof.taskmanager.R.drawable.ic_baseline_expand_less_24)
            } else if (areaHiddenViews[i].visibility != View.GONE) { // View is visible but should be gone
                TransitionManager.beginDelayedTransition(areaCardViews[i], AutoTransition())
                areaHiddenViews[i].visibility = View.GONE
                areaArrows[i].setImageResource(no.gruppe13.hiof.taskmanager.R.drawable.ic_baseline_expand_more_24)
            }
        }
    }

    // Extension function lånt fra https://stackoverflow.com/questions/56624895/android-jetpack-navigation-component-result-from-dialog
    fun <T>Fragment.getNavigationResult(@IdRes id: Int, key: String, onResult: (result: T) -> Unit) {
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
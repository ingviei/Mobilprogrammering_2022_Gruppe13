package no.gruppe13.hiof.taskmanager.ui.calendar

import android.R
import android.icu.util.MeasureUnit.WEEK
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.databinding.FragmentCalendarBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.IsoFields
import java.util.*


class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    private lateinit var arrow: ImageButton
    private lateinit var hiddenView: ConstraintLayout
    private lateinit var cardView: CardView

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

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            cardView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.calendar_card_view)
            arrow = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.arrow_button)
            hiddenView = view.findViewById(no.gruppe13.hiof.taskmanager.R.id.hidden_view)
        } catch(e: Exception) {

        }

        arrow.setOnClickListener { view: View? ->
            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenView.visibility = View.GONE
                arrow.setImageResource(no.gruppe13.hiof.taskmanager.R.drawable.ic_baseline_expand_more_24)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenView.visibility = View.VISIBLE
                arrow.setImageResource(no.gruppe13.hiof.taskmanager.R.drawable.ic_baseline_expand_less_24)
            }
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
        // Finn ut om uka til 31/12 har et høyere ukenummer enn uka før
        if(weekOfLastDate > weekOfPrelastWeek) {
            lastWeekOfYear = weekOfLastDate
        } else {
            lastWeekOfYear = weekOfPrelastWeek
        }

        val weekWeekPicker = requireActivity().findViewById(no.gruppe13.hiof.taskmanager.R.id.week_layout_week_picker) as NumberPicker
        weekWeekPicker.minValue = firstWeekOfYear
        weekWeekPicker.maxValue = lastWeekOfYear
        weekWeekPicker.value = currentWeek
    }

}
package no.gruppe13.hiof.taskmanager.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.databinding.FragmentCalendarBinding
import no.gruppe13.hiof.taskmanager.databinding.FragmentTaskBinding

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val view: View = binding.root

        val textView: TextView = binding.textNotifications
        calendarViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

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

}
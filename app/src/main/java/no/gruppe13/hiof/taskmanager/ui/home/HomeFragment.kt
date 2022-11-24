package no.gruppe13.hiof.taskmanager.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.databinding.FragmentHomeBinding
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAmount

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.floatingActionButtonAddCategoryButton.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationCategoryCreate()
            findNavController().navigate(action)
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

        val todayButton = binding.btnToday //findViewById<Button>(R.id.btnToday)
        todayButton.setOnClickListener{
            val action = HomeFragmentDirections.actionNavigationHomeToTodayFragment()
            findNavController().navigate(action)
        }

        val thisWeekButton = binding.btnThisWeek
        thisWeekButton.setOnClickListener{
            val header = "DENNE UKA"
            var currentDate = java.time.LocalDate.now()
            var endDate = currentDate.plusDays(6)

            val dateFrom:String = currentDate.format(DateTimeFormatter.ISO_DATE)
            val dateTo:String = endDate.format(DateTimeFormatter.ISO_DATE)
            val categoryId = -1
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationTasks(dateFrom, dateTo, categoryId, header)
            findNavController().navigate(action)
        }

/*        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package no.gruppe13.hiof.taskmanager.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationTasks()
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
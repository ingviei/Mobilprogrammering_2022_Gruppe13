package no.gruppe13.hiof.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.databinding.FragmentTaskBinding
import no.gruppe13.hiof.taskmanager.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {
    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodayBinding.inflate(inflater, container, false)
        val view = binding.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addTaskButton = view.findViewById<Button>(R.id.btn_add_task)

        /*addTaskButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.createTaskActivity)
        }*/

    }


}
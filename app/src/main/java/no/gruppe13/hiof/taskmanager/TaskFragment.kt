package no.gruppe13.hiof.taskmanager

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.databinding.FragmentTaskBinding
import no.gruppe13.hiof.taskmanager.ui.home.HomeFragmentDirections

// TODO: Rename parameter arguments, choose names that match


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        val view = binding.root

        val addTaskBtn = binding.floatingActionButtonAddTaskButton
        addTaskBtn.setOnClickListener{
            val action = TaskFragmentDirections.actionNavigationTasksToNavigationCreateTask()
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

        return view
    }

}
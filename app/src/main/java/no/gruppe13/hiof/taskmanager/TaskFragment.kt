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

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
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

        // ToDo: I dette fragmentet burde begge menyvalgene
        //  se ut som om man kan velge dem
        /*
        navView.menu.findItem(R.id.nav_home).isEnabled = true
        navView.menu.findItem(R.id.nav_calendar).isEnabled = true
        navView.menu.findItem(R.id.nav_home).isCheckable = true
        navView.menu.findItem(R.id.nav_calendar).isCheckable = true
        navView.selectedItemId = R.id.nav_calendar
         */

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}
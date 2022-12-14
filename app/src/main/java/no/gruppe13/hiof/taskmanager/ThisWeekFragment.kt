package no.gruppe13.hiof.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.databinding.FragmentThisWeekBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ThisWeekFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentThisWeekBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentThisWeekBinding.inflate(inflater, container, false)
        val view = binding.root

        val navView: BottomNavigationView = binding.navView

        val addTaskBtn = binding.floatingActionButtonAddTaskButton
        addTaskBtn.setOnClickListener{
            val action = ThisWeekFragmentDirections.actionThisWeekFragmentToNavigationCreateTask()
            findNavController().navigate(action)
        }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThisWeekFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThisWeekFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
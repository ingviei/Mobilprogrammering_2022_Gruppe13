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
import no.gruppe13.hiof.taskmanager.CreateCategoryActivity
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

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todayButton = view.findViewById<Button>(R.id.btnToday)
        val thisWeekButton = view.findViewById<Button>(R.id.btnThisWeek)

        todayButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_navigation_home_to_todayFragment)
        }
        thisWeekButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_navigation_home_to_thisWeekFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

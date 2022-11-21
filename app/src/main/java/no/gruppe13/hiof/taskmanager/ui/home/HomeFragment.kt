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
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import no.gruppe13.hiof.taskmanager.CreateCategoryActivity
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.TodayFragment
import no.gruppe13.hiof.taskmanager.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

   private var _binding: FragmentHomeBinding? = null

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
            val context = binding.floatingActionButtonAddCategoryButton.context
            val intent = Intent(context, CreateCategoryActivity::class.java)
            context.startActivity(intent)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController()

        navView.setupWithNavController(navController)

        val todayButton = binding.btnToday //findViewById<Button>(R.id.btnToday)
        todayButton.setOnClickListener{
            Intent(this.requireActivity(), TodayFragment::class.java).also{
                startActivity(it)
            }
        }

        val thisWeekButton = binding.btnThisWeek
        thisWeekButton.setOnClickListener{
            val action = HomeFragmentDirections.actionNavigationHomeToThisWeekFragment2()
            findNavController().navigate(action)
        }



       /* val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        return root
    }

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }*/

   /* binding.floatingActionButtonAddCategoryButton.setOnClickListener {
        val context = binding.floatingActionButtonAddCategoryButton.context
        val intent = Intent(context, CreateCategoryActivity::class.java)
        context.startActivity(intent)
    }*/


    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todayButton = view.findViewById<Button>(R.id.btnToday)
        val thisWeekButton = view.findViewById<Button>(R.id.btnThisWeek)

        todayButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_navigation_home_to_todayFragment2)
        }
        thisWeekButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_navigation_home_to_thisWeekFragment2)
        }

    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
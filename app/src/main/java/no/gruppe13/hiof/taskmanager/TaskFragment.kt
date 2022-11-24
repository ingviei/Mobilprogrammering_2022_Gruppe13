package no.gruppe13.hiof.taskmanager

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.adapter.TaskAdapter
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.FragmentTaskBinding
import no.gruppe13.hiof.taskmanager.ui.home.HomeFragmentDirections
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

// TODO: Rename parameter arguments, choose names that match


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private lateinit var recyclerView: RecyclerView

    // Variable for title to display
    private lateinit var header: String

    // Variables for filtering the tasks
    private var dateFrom: String? = null
    private var dateTo: String? = null
    private var categoryId: Int = -1

    private val viewModel: TaskManagerViewModel by activityViewModels {
        TaskManagerViewModelFactory(
            (activity?.application as TaskManagerApplication).database.taskDao(),
            (activity?.application as TaskManagerApplication).database.categoryDao()
        )
    }

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

        val args: TaskFragmentArgs by navArgs()

        header = args.header
        dateFrom = args.dateFrom
        dateTo = args.dateTo
        categoryId = args.categoryId

        binding.taskHeader.setText(header)

        recyclerView = binding.taskRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val taskAdapter = TaskAdapter()
        recyclerView.adapter = taskAdapter

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                viewModel.getFilteredTasks(dateFrom, dateTo, categoryId).collect() {
                    taskAdapter.submitList(it)
                }
            }
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}
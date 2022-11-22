package no.gruppe13.hiof.taskmanager

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.text.style.StrikethroughSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.adapter.TaskAdapter
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.data.TaskDao
import no.gruppe13.hiof.taskmanager.databinding.FragmentTodayBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

class TodayFragment : Fragment() {

    private var _binding: FragmentTodayBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: TaskManagerViewModel by activityViewModels {
        TaskManagerViewModelFactory(
            (activity?.application as TaskManagerApplication).database.taskDao(),
            (activity?.application as TaskManagerApplication).database.categoryDao()
        )
    }

    private fun loadTasks() {
        val taskList = <Task>(TaskDao.)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_today, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rvToday
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val taskAdapter = TaskAdapter { TaskDao.getAllTasks() }
        recyclerView.adapter = taskAdapter

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                viewModel.allTasks().collect() {
                    taskAdapter.submitList(it)
                }
            }
        }



        // For å streke gjennom item
       /* private fun toggleStrikeThrough(tvTaskTitle: TextView, completed: Boolean) {
            if (completed) {
                tvTaskTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                tvTaskTitle.paintFlags = tvTaskTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }
        }*/

        //val checkbox = view.findViewById<CheckBox>(R.id.checkBoxItem)

        //Wrap en funksjon rundt denne
       /* toggleStrikeThrough(tvTaskTitle, currentTask.completed)
        checkbox.setOnCheckedChangeListener { _, _ ->
            toggleStrikeThrough(tvTaskTitle, completed)
            currentTask.completed = !currentTask.completed
        }*/

    }


       /* val addTaskButton = view.findViewById<Button>(R.id.btn_add_task)

        addTaskButton.setOnClickListener{
            val navController = it.findNavController()
            navController.navigate(R.id.action_todayFragment_to_navigation_create_task)
        }*/

    }



package no.gruppe13.hiof.taskmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.adapter.TaskAdapter
import no.gruppe13.hiof.taskmanager.databinding.FragmentTodayBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

class TodayFragment : Fragment() {

   /* private var param1: String? = null
    private var param2: String? = null*/

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: TaskManagerViewModel by activityViewModels {
        TaskManagerViewModelFactory(
            (activity?.application as TaskManagerApplication).database.taskDao(),
            (activity?.application as TaskManagerApplication).database.categoryDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.todayRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val taskAdapter = TaskAdapter()
        recyclerView.adapter = taskAdapter

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                viewModel.allTasks().collect() {
                    taskAdapter.submitList(it)
                }
            }
        }

         binding.btnAddTask.setOnClickListener {
            val navController = it.findNavController()
            navController.navigate(R.id.action_todayFragment_to_navigation_create_task)
        }

       /*binding.btnDeleteTask.setOnClickListener {
           CoroutineScope(view.context).launch(Dispatchers.IO) {
               lifecycle.coroutineScope.launch {
                   viewModel.deleteCompleted()
                   Log.d("Slett", "Det slettes")
               }*/
           }



        // Alternativ metode:
        /*binding.btnAddTask.setOnClickListener {
            val action = TodayFragmentDirections.actionTodayFragmentToNavigationCreateTask()
            val navController = it.findNavController()
            navController.navigate(action)
        }*/


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodayFragment.
         */
        // TODO: Rename and change types and number of parameters

        @JvmStatic
        fun newInstance() =
            TodayFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}




package no.gruppe13.hiof.taskmanager

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.text.set
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.adapter.CreateTaskCategorySpinnerAdapter
import no.gruppe13.hiof.taskmanager.data.Task
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.FragmentCreateTaskBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCreateTaskBinding

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
        binding = FragmentCreateTaskBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        val view = binding.root

        //ToDo: Sjekk om dette er det beste stedet å deklarere viewmodel
        val viewModel =
            TaskManagerViewModel(
                (this.requireActivity().application as TaskManagerApplication).database.taskDao(),
                (this.requireActivity().application as TaskManagerApplication).database.categoryDao()
            )

        val chooseCategorySpinner: Spinner = binding.pickCategorySpinner
        val spinnerAdapter = CreateTaskCategorySpinnerAdapter(this.requireActivity(), R.layout.simple_spinner_item, ArrayList<Category>())
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        chooseCategorySpinner.adapter = spinnerAdapter

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                viewModel.allCategories().collect() {
                    spinnerAdapter.clear()
                    spinnerAdapter.addAll(it)

                }
            }
        }

        requireActivity().supportFragmentManager
            .setFragmentResultListener(DatePickerFragment.REQUEST_KEY, this) { requestKey, bundle ->
                val result = bundle.getString(DatePickerFragment.FORMATTED_DATE_KEY)
                binding.dateInput.setText(result)
            }

        requireActivity().supportFragmentManager
            .setFragmentResultListener(TimePickerFragment.REQUEST_KEY, this) { requestKey, bundle ->
                val result = bundle.getString(TimePickerFragment.FORMATTED_TIME_KEY)
                binding.timeInput.setText(result)
            }

        val navController = findNavController();
        // Lånt fra https://stackoverflow.com/questions/56624895/android-jetpack-navigation-component-result-from-dialog
        /*navController.currentBackStackEntry?.savedStateHandle?.getLiveData("key")?.observe(
            viewLifecycleOwner) { result: Date ->
            // Do something with the result.
        }*/

        binding.createTaskButton.setOnClickListener {
            val context = binding.createTaskButton.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

            val selectedCategory: Category = binding.pickCategorySpinner.selectedItem as Category
            val task = Task(
                binding.taskInput.text.toString(),
                //ToDo: Fix inconsistent data types PK Category vs FK Task->Category
                selectedCategory.id.toLong(),
                binding.dateInput.text.toString(),
                binding.timeInput.text.toString(),
                binding.commentInput.text.toString(),
                false
            )

            val db = TaskDatabase.getDatabase(this.requireActivity())
            lifecycleScope.launch{
                db.taskDao().insertTask(task)
            }

            val toast = Toast.makeText(this.requireActivity(), "Gjøremål opprettet!", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.pickDateCreateTaskBtn.setOnClickListener() {
            showDatePicker(it)
        }

        binding.pickTimeCreateTaskBtn.setOnClickListener() {
            showTimePicker(it)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavigationResult<String>(
            Navigation.findNavController(this.requireView()).currentDestination!!.id,
            "picked_date") { setDateText(it) }

        getNavigationResult<String>(
            Navigation.findNavController(this.requireView()).currentDestination!!.id,
            "picked_time") { setTimeText(it) }
    }

    fun showDatePicker(v: View) {
        val action = CreateTaskFragmentDirections.actionNavigationCreateTaskToNavigationDatePicker()
        findNavController().navigate(action)
    }

    fun showTimePicker(v: View) {
        val action = CreateTaskFragmentDirections.actionNavigationCreateTaskToNavigationTimePicker()
        findNavController().navigate(action)
    }

    fun setDateText(dateString: String) {
        binding.dateInput.setText(dateString)
    }

    fun setTimeText(timeString: String) {
        binding.timeInput.setText(timeString)
    }

    // Extension function lånt fra https://stackoverflow.com/questions/56624895/android-jetpack-navigation-component-result-from-dialog
    fun <T>Fragment.getNavigationResult(@IdRes id: Int, key: String, onResult: (result: T) -> Unit) {
        val navBackStackEntry = findNavController().getBackStackEntry(id)

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME
                && navBackStackEntry.savedStateHandle.contains(key)
            ) {
                val result = navBackStackEntry.savedStateHandle.get<T>(key)
                result?.let(onResult)
                navBackStackEntry.savedStateHandle.remove<T>(key)
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)

        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
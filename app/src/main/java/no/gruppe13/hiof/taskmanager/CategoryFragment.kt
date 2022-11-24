package no.gruppe13.hiof.taskmanager

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.adapter.CategoryAdapter
import no.gruppe13.hiof.taskmanager.databinding.FragmentCategoryBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

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
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.categoryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        registerForContextMenu(recyclerView)

        val categoryAdapter = CategoryAdapter() {
            Toast.makeText(view.context, "onclicked", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = categoryAdapter

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                viewModel.allCategories().collect() {
                    categoryAdapter.submitList(it)
                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_category -> {
                findNavController().navigate(R.id.action_navigation_home_to_categoryFragment)
            }
            R.id.delete_category -> {
                Toast.makeText(this.context, "delete clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
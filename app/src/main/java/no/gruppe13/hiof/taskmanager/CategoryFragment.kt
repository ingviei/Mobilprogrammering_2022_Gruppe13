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
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.ActivityCreateTaskBinding
import no.gruppe13.hiof.taskmanager.databinding.FragmentCategoryBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_category, container, false)

        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    public fun onItemClicked(category: Category) {
        return
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
                findNavController().navigate(R.id.action_navigation_home_to_navigation_category_update)
            }
            R.id.delete_category -> {
                Toast.makeText(this.context, "delete clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package no.gruppe13.hiof.taskmanager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.FragmentTodayBinding
import no.gruppe13.hiof.taskmanager.databinding.FragmentUpdateCategoryBinding
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModel
import no.gruppe13.hiof.taskmanager.viewmodels.TaskManagerViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CATEGORY_ID = UpdateCategoryFragment.ARG_CATEGORY_ID

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryId: Int? = null
    private var _binding: FragmentUpdateCategoryBinding? = null
    private var category: Category? = null

    private val binding get() = _binding!!

    private val viewModel: TaskManagerViewModel by activityViewModels {
        TaskManagerViewModelFactory(
            (activity?.application as TaskManagerApplication).database.taskDao(),
            (activity?.application as TaskManagerApplication).database.categoryDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val categoryId = arguments?.let { UpdateCategoryFragmentArgs.fromBundle(it).categoryId }
        // Inflate the layout for this fragment

       _binding = FragmentUpdateCategoryBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        GlobalScope.launch(Dispatchers.IO) {
            lifecycle.coroutineScope.launch {
                if (categoryId != null) {
                    viewModel.getCategory(categoryId).collect() {
                        binding.updateCategoryInput.setText(it.title)
                        binding.updateCommentInput.setText(it.description)
                        category = it
                    }
                }
            }
        }

        binding.updateCategoryButton.setOnClickListener {
            val context = binding.updateCategoryButton.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

            category?.title = binding.updateCategoryInput.text.toString()
            category?.description = binding.updateCommentInput.text.toString()

            val db = TaskDatabase.getDatabase(this.requireActivity())
            lifecycleScope.launch{
                category?.let { it1 -> db.categoryDao().updateCategory(it1) }
            }

            val toast = Toast.makeText(this.requireActivity(), "Kategori endret!", Toast.LENGTH_SHORT)
            toast.show()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param categoryId Id of the Category to update.
         * @return A new instance of fragment UpdateCategoryFragment.
         */
        @JvmStatic
        fun newInstance(categoryId: Int) =
            UpdateCategoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CATEGORY_ID, categoryId)
                }
            }

        const val ARG_CATEGORY_ID = "param_category_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
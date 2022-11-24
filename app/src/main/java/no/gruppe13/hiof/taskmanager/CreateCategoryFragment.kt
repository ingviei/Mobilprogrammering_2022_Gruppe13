package no.gruppe13.hiof.taskmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.FragmentCreateCategoryBinding

class CreateCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentCreateCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateCategoryBinding.inflate(layoutInflater)
        val view = binding.root

        binding.createCategoryButton.setOnClickListener {
            val action = CreateCategoryFragmentDirections.actionNavigationCategoryCreateToNavigationHome()
            findNavController().navigate(action)

            val category = Category(
                0, binding.categoryInput.text.toString(), binding.commentInput.text.toString()
            )

            val db = TaskDatabase.getDatabase(requireActivity())
            lifecycleScope.launch{
                db.categoryDao().insertCategory(category)
            }

            val toast = Toast.makeText(requireActivity(), "Kategori opprettet!", Toast.LENGTH_SHORT)
            toast.show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
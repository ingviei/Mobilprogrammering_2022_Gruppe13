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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentCreateCategoryBinding
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentCreateCategoryBinding.inflate(layoutInflater)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
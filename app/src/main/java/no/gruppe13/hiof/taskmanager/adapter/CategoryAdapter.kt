package no.gruppe13.hiof.taskmanager.adapter

// The code for managing database and files that tie it together are greatly inspired by Android's tutorial:
// https://developer.android.com/courses/pathways/android-basics-kotlin-unit-5-pathway-1#codelab-https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.gruppe13.hiof.taskmanager.CategoryFragment
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.UpdateCategoryFragment
import no.gruppe13.hiof.taskmanager.data.TaskDatabase
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.CategoryListItemBinding
import no.gruppe13.hiof.taskmanager.ui.home.HomeFragmentDirections
import java.nio.file.Files.delete

//class CategoryAdapter(/*private val onItemClicked: (Category) -> Unit*/) :
class CategoryAdapter(private val clickListener: View.OnClickListener) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback) {
    private var position = 0

    override
    fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val viewHolder = CategoryViewHolder(
            CategoryListItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            //onItemClicked(getItem(position))
        }
        return viewHolder
    }

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }
    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class CategoryViewHolder(private var binding: CategoryListItemBinding): RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
//    class CategoryViewHolder(private var binding: CategoryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        private var categoryId: Int = 0
        private lateinit var onChange: CategoryOnMenuItemClickListener

        fun bind(category: Category, clickListener: View.OnClickListener) {
            binding.categoryName.text = category.title
            categoryId = category.id

            itemView.setOnClickListener(clickListener)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {

            val inflater: MenuInflater = this.itemView.findFragment<CategoryFragment>().requireActivity().menuInflater
            inflater.inflate(R.menu.category_context_menu, menu)

            val onChange: CategoryOnMenuItemClickListener = CategoryOnMenuItemClickListener(v, categoryId)

            var i: Int = 0
            // ToDo: Se på denne
            while(i < menu.size()) {
                menu.getItem(i).setOnMenuItemClickListener(onChange)
                i++
            }

        }
    }

    class CategoryOnMenuItemClickListener(private val view: View,
                                          private val categoryId: Int
                                          ) : MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.edit_category -> {
                    val action = HomeFragmentDirections.actionNavigationHomeToNavigationCategoryUpdate(categoryId)

                    view.findNavController().navigate(action)
                    return true
                }
                R.id.delete_category -> {
                    val db = TaskDatabase.getDatabase(view.findFragment<Fragment>().requireActivity())

                    view.findFragment<Fragment>().lifecycleScope.launch() {
                        try {
                            db.categoryDao().deleteCategory(categoryId)
                        }
                        catch(e: Exception) {
                            // Prevents application crash on older phone models
                        }
                    }

                    Toast.makeText(view.context, "Kategori slettet!", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
            return false
        }
    }



    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    }
}
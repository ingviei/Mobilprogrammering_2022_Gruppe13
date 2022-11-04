package no.gruppe13.hiof.taskmanager.adapter

// The code for managing database and files that tie it together are greatly inspired by Android's tutorial:
// https://developer.android.com/courses/pathways/android-basics-kotlin-unit-5-pathway-1#codelab-https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.FlowCollector
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.CategoryListItemBinding

class CategoryAdapter(/*private val onItemClicked: (Category) -> Unit*/) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback) {
    override
    fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val viewHolder = CategoryViewHolder(
            CategoryListItemBinding.inflate(LayoutInflater.from( parent.context),
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

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(private var binding: CategoryListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.categoryName.text = category.title
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
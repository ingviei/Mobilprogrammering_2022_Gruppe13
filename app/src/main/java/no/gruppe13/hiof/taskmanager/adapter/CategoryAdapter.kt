package no.gruppe13.hiof.taskmanager.adapter

// The code for managing database and files that tie it together are greatly inspired by Android's tutorial:
// https://developer.android.com/courses/pathways/android-basics-kotlin-unit-5-pathway-1#codelab-https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.*
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import no.gruppe13.hiof.taskmanager.CategoryFragment
import no.gruppe13.hiof.taskmanager.R
import no.gruppe13.hiof.taskmanager.data.category.Category
import no.gruppe13.hiof.taskmanager.databinding.CategoryListItemBinding
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


        fun bind(category: Category, clickListener: View.OnClickListener) {
            binding.categoryName.text = category.title
            itemView.setOnClickListener(clickListener)
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            /*menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "SMS");*/

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
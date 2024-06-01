package lib.shug.test_deveem.ui.products.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lib.shug.test_deveem.databinding.ItemCategoriesBinding
import lib.shug.test_deveem.utils.capitalizeWords

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var onItemClick: ((category: String) -> Unit)? = null
    private var categoriesList = listOf<String>()
    private var selectedPosition = RecyclerView.NO_POSITION

    fun setFirstItemChecked() {
        if (itemCount > 0) {
            selectedPosition = 0
            notifyItemChanged(0)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCategories(cats: List<String>) {
        this.categoriesList = cats
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            with(binding) {
                rbCategory.text = category.capitalizeWords()
                rbCategory.isChecked = selectedPosition == position
                rbCategory.setOnClickListener {
                    if (selectedPosition != position) {
                        onItemClick?.invoke(category)
                        val oldSelectedPosition = selectedPosition
                        selectedPosition = position
                        notifyItemChanged(oldSelectedPosition)
                        notifyItemChanged(selectedPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    override fun getItemCount() = categoriesList.size
}
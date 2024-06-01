package lib.shug.test_deveem.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import lib.shug.test_deveem.databinding.ItemProductBinding
import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.utils.loadImage

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    var onItemClick: ((id: Int) -> Unit)? = null
    private var productList = listOf<ProductEntity>()

    fun setProducts(prods: List<ProductEntity>) {
        val diffResult = DiffUtil.calculateDiff(ProductsDiffUtil(productList, prods))
        this.productList = prods
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductEntity) {
            with(binding) {
                tvPrice.text = product.price.toString()
                tvName.text = product.title
                imgProduct.loadImage(product.image)
                cvProduct.setOnClickListener {
                    onItemClick?.invoke(product.id)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size
}

class ProductsDiffUtil(
    private val oldItems: List<ProductEntity>,
    private val newItems: List<ProductEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}
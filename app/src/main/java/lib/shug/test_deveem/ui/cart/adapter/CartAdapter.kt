package lib.shug.test_deveem.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import lib.shug.test_deveem.databinding.ItemCartBinding
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.utils.loadImage

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var onRemoveCartClick: ((id: Int) -> Unit)? = null
    private var cartList = listOf<CartEntity>()

    fun setCart(prods: List<CartEntity>) {
        val diffResult = DiffUtil.calculateDiff(CartDiffUtil(cartList, prods))
        this.cartList = prods
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cart: CartEntity) {
            with(binding) {
                tvPrice.text = cart.price.toString()
                tvName.text = cart.title
                imgProduct.loadImage(cart.image)
                btnRemoveCart.setOnClickListener {
                    onRemoveCartClick?.invoke(cart.id)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount() = cartList.size
}

class CartDiffUtil(
    private val oldItems: List<CartEntity>,
    private val newItems: List<CartEntity>
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
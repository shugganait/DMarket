package lib.shug.test_deveem.ui.product_bottom_sheet

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import lib.shug.test_deveem.R
import lib.shug.test_deveem.databinding.ProductBottomSheetFragmentBinding
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.utils.Constants.PRODUCT_ID_KEY
import lib.shug.test_deveem.utils.loadImage

@AndroidEntryPoint
class ProductBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<ProductBottomSheetViewModel>()
    private lateinit var binding: ProductBottomSheetFragmentBinding
    private var isInCart = false
    private lateinit var currentProduct: ProductEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ProductBottomSheetFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(PRODUCT_ID_KEY)?.let {
            isProductInCart(it)
            viewModel.getProductById(it)
        }
        observe()
        initListeners()
    }

    @SuppressLint("ResourceType")
    private fun initListeners() = with(binding) {
        btnCart.setOnClickListener {
            if (isInCart) {
                findNavController().navigate(R.id.cartFragment)
            } else {
                insertCart()
                setBtnCartNewStatus()
            }
        }
    }

    private fun insertCart() {
        val cart = CartEntity(
            currentProduct.id,
            currentProduct.title,
            currentProduct.price,
            currentProduct.image
        )
        viewModel.insertCart(cart)
    }

    private fun observe() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            products?.let {
                currentProduct = it
                initViews(it)
            }
        }
    }

    private fun initViews(product: ProductEntity) = with(binding) {
        tvName.text = product.title
        tvCategory.text = product.category
        tvPrice.text = product.price.toString()
        rbRating.rating = product.rating.toFloat()
        imgProduct.loadImage(product.image)
        tvDesc.text = product.description
    }

    private fun isProductInCart(productId: Int) {
        viewModel.getAllCart()
        viewModel.cart.observe(viewLifecycleOwner) { cart ->
            if (containsProductWithId(cart, productId)) {
                isInCart = true
                setBtnCartNewStatus()
            } else {
                isInCart = false
            }
        }
    }

    private fun containsProductWithId(cart: List<CartEntity>, id: Int): Boolean {
        return cart.any { it.id == id }
    }

    private fun setBtnCartNewStatus() = with(binding) {
        btnCart.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.black)
        btnCart.text = getText(R.string.go_to_cart)
        btnCart.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.white))
        val drawable: Drawable? =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_shop_cart)
        btnCart.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

}
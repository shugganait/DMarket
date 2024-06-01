package lib.shug.test_deveem.ui.cart

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import lib.shug.test_deveem.R
import lib.shug.test_deveem.databinding.FragmentCartBinding
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.ui.cart.adapter.CartAdapter
import lib.shug.test_deveem.ui.base.BaseFragment
import lib.shug.test_deveem.utils.showToast
import kotlin.math.abs

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val viewModel by viewModels<CartViewModel>()
    private val cartAdapter = CartAdapter()
    private var isCollapsed = false
    private var isEmpty = true
    private var localCart = emptyList<CartEntity>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding = FragmentCartBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.cart.observe(viewLifecycleOwner) { cart ->
            if (cart.isEmpty()) {
                localCart = cart
                isEmpty = true
                enableEmptyState(b1 = true, b2 = false)
            } else {
                cartAdapter.setCart(cart)
                localCart = cart
                isEmpty = false
                enableEmptyState(b1 = false, b2 = true)
            }
        }
    }

    private fun enableEmptyState(b1: Boolean, b2: Boolean) {
        binding.tvEmpty.isVisible = b1
        binding.rvCart.isVisible = b2
        if (b1) {
            setBtnCartNewStatus()
        }
    }

    override fun collectData() {
        viewModel.getAllCart()
    }

    override fun initView() = with(binding) {
        rvCart.adapter = cartAdapter
    }

    override fun initListeners() = with(binding){

        appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            val alpha = 1.0f - abs(verticalOffset / scrollRange.toFloat())
            imgLogo.alpha = alpha

            if (abs(verticalOffset) >= scrollRange) {
                activity?.window?.let { window ->
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.grey_average)
                    WindowInsetsControllerCompat(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = false
                }
                isCollapsed = true
            } else {
                activity?.window?.let { window ->
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.grey_light)
                    WindowInsetsControllerCompat(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = true
                }
                isCollapsed = false
            }
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        cartAdapter.onRemoveCartClick = {
            viewModel.deleteCartById(it)
        }

        btnBuy.setOnClickListener {
            if (isEmpty) {
                findNavController().navigateUp()
            } else {
                context?.showToast("$localCart")
                viewModel.deleteAllCart()
                isEmpty = true
            }
        }
    }

    private fun setBtnCartNewStatus() = with(binding) {
        btnBuy.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.grey_light)
        btnBuy.text = getText(R.string.add_something)
        btnBuy.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.black))
        val drawable: Drawable? =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_basket_outline)
        btnBuy.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

}
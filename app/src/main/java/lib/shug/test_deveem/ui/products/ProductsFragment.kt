package lib.shug.test_deveem.ui.products

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import lib.shug.test_deveem.R
import lib.shug.test_deveem.databinding.DialogCategoriesBinding
import lib.shug.test_deveem.databinding.FragmentProductsBinding
import lib.shug.test_deveem.model.remote.api_models.CategoriesModel
import lib.shug.test_deveem.ui.products.adapter.CategoriesAdapter
import lib.shug.test_deveem.ui.products.adapter.ProductsAdapter
import lib.shug.test_deveem.ui.product_bottom_sheet.ProductBottomSheetFragment
import lib.shug.test_deveem.utils.Constants.PRODUCT_ID_KEY
import lib.shug.test_deveem.model.utils.Resource
import lib.shug.test_deveem.ui.base.BaseFragment
import lib.shug.test_deveem.utils.Constants.SHUG
import kotlin.math.abs


@AndroidEntryPoint
class ProductsFragment : BaseFragment<FragmentProductsBinding>() {

    private val viewModel by viewModels<ProductsViewModel>()
    private val productAdapter = ProductsAdapter()
    private val categoriesAdapter = CategoriesAdapter()
    private var isCollapsed = false
    private var chosenCategory = "all"
    private lateinit var categoriesList: CategoriesModel
    private var categoriesDialog: Dialog? = null
    private var categoriesDialogBinding: DialogCategoriesBinding? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductsBinding = FragmentProductsBinding.inflate(layoutInflater)

    override fun observeData() {
        viewModel.allProducts.observe(viewLifecycleOwner) { products ->
            products?.let { productAdapter.setProducts(it) }
        }
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            when (categories) {
                is Resource.Loading -> {
                    Log.e(SHUG, "getCategories: Loading")
                }

                is Resource.Success -> {
                    categoriesList = categories.data!!
                }

                is Resource.Error -> {
                    val errorMessage = categories.message
                    Log.e(SHUG, "getCategories: $errorMessage")
                }
            }
        }
    }

    override fun collectData() {
        viewModel.getAllProducts()
        viewModel.getCategories()
    }

    override fun initListeners() = with(binding) {
        btnCart.setOnClickListener {
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToCartFragment())
        }
        btnCategory.setOnClickListener {
            if (!categoriesList.isEmpty()) {
                openCategoriesDialog()
            }
        }
        productAdapter.onItemClick = {
            val bottomSheetFragment = ProductBottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
            bottomSheetFragment.arguments = bundleOf(PRODUCT_ID_KEY to it)
        }
        collapsingToolbarListener()
    }

    override fun initView() = with(binding) {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rvProducts.layoutManager = layoutManager
        rvProducts.adapter = productAdapter
    }

    private fun openCategoriesDialog() {
        categoriesDialog = Dialog(requireContext())
        categoriesDialog?.setContentView(R.layout.dialog_categories)
        categoriesDialogBinding =
            DialogCategoriesBinding.bind((categoriesDialog!!.findViewById<LinearLayout>(R.id.dialog_categories)))

        //listeners
        categoriesAdapter.onItemClick = {
            chosenCategory = it
        }
        categoriesDialogBinding?.btnAccept?.setOnClickListener {
            if (chosenCategory != "all") {
                viewModel.getProductsByCategory(chosenCategory)
            } else {
                viewModel.getAllProducts()
            }
            binding.tvCategory.text = chosenCategory.uppercase()
            binding.tvCategory.post {
                if (isCollapsed) {
                    val translationX = (binding.products.width - binding.tvCategory.width) / 2f
                    tvCentering(translationX)
                }
            }
            categoriesDialog?.dismiss()
        }
        categoriesDialogBinding?.btnDecline?.setOnClickListener {
            categoriesDialog?.dismiss()
        }

        //initializing adapter
        categoriesDialogBinding?.rvCategories?.adapter = categoriesAdapter
        categoriesAdapter.setCategories(categoriesList)
        if (chosenCategory == "all") {
            categoriesAdapter.setFirstItemChecked()
        }

        categoriesDialog?.show()

        // settings for custom dialog
        val window: Window? = categoriesDialog?.window
        window?.setLayout(
            CoordinatorLayout.LayoutParams.MATCH_PARENT,
            CoordinatorLayout.LayoutParams.WRAP_CONTENT
        )
        categoriesDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun collapsingToolbarListener() = with(binding) {
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
            } else {
                activity?.window?.let { window ->
                    window.statusBarColor =
                        ContextCompat.getColor(requireContext(), R.color.grey_light)
                    WindowInsetsControllerCompat(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = true
                }
            }
            val currentHeight = scrollRange + verticalOffset

            if (currentHeight < 152 && !isCollapsed) {
                //centering tvCategory
                isCollapsed = true

                val translationX = products.width / 2 - tvCategory.width / 2

                tvMovingAnimation(translationX.toFloat())
            } else if (currentHeight > 20 && isCollapsed) {
                //moving to starting pos
                isCollapsed = false

                tvMovingAnimation(0f)
            }

        }
    }

    private fun tvMovingAnimation(sum: Float) = with(binding) {
        ViewCompat.animate(tvCategory)
            .translationX(sum)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun tvCentering(sum: Float) = with(binding) {
        ViewCompat.animate(tvCategory)
            .setDuration(0)
            .translationX(sum)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
}
package lib.shug.test_deveem.ui.product_bottom_sheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.repository.use_cases.local.cart.GetAllCartDBUseCase
import lib.shug.test_deveem.repository.use_cases.local.cart.InsertCartDBUseCase
import lib.shug.test_deveem.repository.use_cases.local.product.GetProductByIdDBUseCase
import javax.inject.Inject

@HiltViewModel
class ProductBottomSheetViewModel @Inject constructor(
    private val getProductByIdDBUseCase: GetProductByIdDBUseCase,
    private val getAllCartDBUseCase: GetAllCartDBUseCase,
    private val insertCartDBUseCase: InsertCartDBUseCase,
) : ViewModel() {

    val products = MutableLiveData<ProductEntity>()

    val cart = MutableLiveData<List<CartEntity>>()

    fun getProductById(id: Int) {
        viewModelScope.launch {
            getProductByIdDBUseCase.getProductById(id).collect {
                products.postValue(it)
            }
        }
    }

    fun getAllCart() {
        viewModelScope.launch {
            getAllCartDBUseCase.getAllProducts().collect {
                cart.postValue(it)
            }
        }
    }

    fun insertCart(cart: CartEntity) {
        viewModelScope.launch {
            insertCartDBUseCase.invoke(cart)
        }
    }
}
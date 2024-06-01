package lib.shug.test_deveem.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.repository.use_cases.local.cart.DeleteAllCartDBUseCase
import lib.shug.test_deveem.repository.use_cases.local.cart.DeleteCartByIdDBUseCase
import lib.shug.test_deveem.repository.use_cases.local.cart.GetAllCartDBUseCase
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartDBUseCase: GetAllCartDBUseCase,
    private val deleteCartByIdDBUseCase: DeleteCartByIdDBUseCase,
    private val deleteAllCartDBUseCase: DeleteAllCartDBUseCase
) : ViewModel() {

    val cart = MutableLiveData<List<CartEntity>>()

    fun getAllCart() {
        viewModelScope.launch {
            getAllCartDBUseCase.getAllProducts().collect {
                cart.postValue(it)
            }
        }
    }

    fun deleteAllCart() {
        viewModelScope.launch {
            deleteAllCartDBUseCase.invoke()
        }
    }

    fun deleteCartById(id: Int) {
        viewModelScope.launch {
            deleteCartByIdDBUseCase.invoke(id)
        }
    }
}
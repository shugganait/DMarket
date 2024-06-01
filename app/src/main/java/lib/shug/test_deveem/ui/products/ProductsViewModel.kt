package lib.shug.test_deveem.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.model.remote.api_models.CategoriesModel
import lib.shug.test_deveem.repository.use_cases.local.product.GetAllProductsDBUseCase
import lib.shug.test_deveem.repository.use_cases.remote.GetCategoriesUseCase
import lib.shug.test_deveem.repository.use_cases.local.product.GetProductsByCategoryDBUseCase
import lib.shug.test_deveem.model.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getAllProductsDBUseCase: GetAllProductsDBUseCase,
    private val getProductsByCategoryDBUseCase: GetProductsByCategoryDBUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val mutableCategories = MutableLiveData<Resource<CategoriesModel>>()
    val categories: LiveData<Resource<CategoriesModel>> get() = mutableCategories
    val allProducts = MutableLiveData<List<ProductEntity>>()

    fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect { resource ->
                resource.data?.add(0, "all")
                mutableCategories.postValue(resource)
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductsDBUseCase.getAllProducts().collect {
                allProducts.postValue(it)
            }
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            getProductsByCategoryDBUseCase.getProductsByCategory(category).collect {
                allProducts.postValue(it)
            }
        }
    }

}
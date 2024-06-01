package lib.shug.test_deveem.ui.activities.splash_activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.repository.use_cases.local.product.DeleteAllProductsDBUseCase
import lib.shug.test_deveem.repository.use_cases.remote.GetAllProductsUseCase
import lib.shug.test_deveem.repository.use_cases.local.product.InsertProductDBUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val insertProductDBUseCase: InsertProductDBUseCase,
    private val deleteAllProductsDBUseCase: DeleteAllProductsDBUseCase
) : ViewModel() {

    fun deleteAll() = deleteAllProductsDBUseCase.invoke()

    fun getAllProducts() = getAllProductsUseCase.invoke()

    fun insertProductDB(product: ProductEntity) = insertProductDBUseCase.invoke(product)
}
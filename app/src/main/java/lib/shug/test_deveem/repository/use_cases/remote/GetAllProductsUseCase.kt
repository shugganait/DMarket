package lib.shug.test_deveem.repository.use_cases.remote

import lib.shug.test_deveem.repository.remote.ProductsRepository
import javax.inject.Inject

class GetAllProductsUseCase  @Inject constructor(private val repository: ProductsRepository) {

    fun invoke() = repository.getAllProducts()

}
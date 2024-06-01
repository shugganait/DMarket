package lib.shug.test_deveem.repository.use_cases.local.product

import lib.shug.test_deveem.repository.local.products.LocalProductsRepository
import javax.inject.Inject

class GetProductByIdDBUseCase  @Inject constructor(private val repository: LocalProductsRepository) {

    fun getProductById(id: Int) = repository.getProductById(id)

}
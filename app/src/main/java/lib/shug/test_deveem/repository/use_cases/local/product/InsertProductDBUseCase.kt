package lib.shug.test_deveem.repository.use_cases.local.product

import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.repository.local.products.LocalProductsRepository
import javax.inject.Inject

class InsertProductDBUseCase @Inject constructor(private val repository: LocalProductsRepository) {

    fun invoke(product: ProductEntity) = repository.insertProduct(product)

}
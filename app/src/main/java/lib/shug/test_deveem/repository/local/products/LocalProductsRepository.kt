package lib.shug.test_deveem.repository.local.products

import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.local.entities.ProductEntity

interface LocalProductsRepository {
    fun getAllProducts(): Flow<List<ProductEntity>>
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>
    fun getProductById(id: Int): Flow<ProductEntity>
    fun insertProduct(product: ProductEntity)
    fun deleteAll()
}
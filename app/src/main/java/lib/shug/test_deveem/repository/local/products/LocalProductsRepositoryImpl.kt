package lib.shug.test_deveem.repository.local.products

import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.local.Dao
import lib.shug.test_deveem.model.local.entities.ProductEntity
import javax.inject.Inject

class LocalProductsRepositoryImpl @Inject constructor(private val dao: Dao) :
    LocalProductsRepository {
    override fun getAllProducts(): Flow<List<ProductEntity>> {
        return dao.getAllProducts()
    }

    override fun getProductsByCategory(category: String): Flow<List<ProductEntity>> {
        return dao.getProductsByCategory(category)
    }

    override fun getProductById(id: Int): Flow<ProductEntity> {
        return dao.getProductById(id)
    }

    override fun insertProduct(product: ProductEntity) {
        dao.insertProduct(product)
    }

    override fun deleteAll() {
        dao.deleteAllProducts()
    }


}
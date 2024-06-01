package lib.shug.test_deveem.repository.remote

import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.remote.api_models.CategoriesModel
import lib.shug.test_deveem.model.remote.api_models.ProductsModel
import lib.shug.test_deveem.model.utils.Resource

interface ProductsRepository {

    fun getAllProducts(): Flow<Resource<ProductsModel>>

    fun getCategories(): Flow<Resource<CategoriesModel>>

}
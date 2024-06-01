package lib.shug.test_deveem.repository.remote

import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.remote.api_models.CategoriesModel
import lib.shug.test_deveem.model.remote.api_models.ProductsModel
import lib.shug.test_deveem.model.remote.api_services.ProductsApiService
import lib.shug.test_deveem.model.utils.Resource
import lib.shug.test_deveem.repository.base.BaseRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val api: ProductsApiService) : ProductsRepository,
    BaseRepository() {

    override fun getAllProducts(): Flow<Resource<ProductsModel>> =
        doRequest { api.getAllProducts() }

    override fun getCategories(): Flow<Resource<CategoriesModel>> = doRequest {
        api.getCategories()
    }

}
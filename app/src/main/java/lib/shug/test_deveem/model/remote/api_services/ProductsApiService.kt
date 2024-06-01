package lib.shug.test_deveem.model.remote.api_services

import lib.shug.test_deveem.model.remote.api_models.CategoriesModel
import lib.shug.test_deveem.model.remote.api_models.ProductsModel
import retrofit2.http.GET

interface ProductsApiService {

    @GET("products/")
    suspend fun getAllProducts(): ProductsModel

    @GET("products/categories")
    suspend fun getCategories(): CategoriesModel

}
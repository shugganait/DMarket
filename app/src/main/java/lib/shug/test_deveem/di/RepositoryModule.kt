package lib.shug.test_deveem.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lib.shug.test_deveem.repository.local.cart.LocalCartRepository
import lib.shug.test_deveem.repository.local.cart.LocalCartRepositoryImpl
import lib.shug.test_deveem.repository.local.products.LocalProductsRepository
import lib.shug.test_deveem.repository.local.products.LocalProductsRepositoryImpl
import lib.shug.test_deveem.repository.remote.ProductsRepository
import lib.shug.test_deveem.repository.remote.ProductsRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    fun provideProductsRepository(productsRepositoryImpl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    fun provideLocalProductsRepository(localProductsRepositoryImpl: LocalProductsRepositoryImpl): LocalProductsRepository

    @Binds
    fun provideLocalCartRepository(localCartRepositoryImpl: LocalCartRepositoryImpl): LocalCartRepository
}
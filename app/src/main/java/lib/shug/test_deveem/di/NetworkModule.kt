package lib.shug.test_deveem.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lib.shug.test_deveem.model.remote.RetrofitClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient() = RetrofitClient()

    @Provides
    @Singleton
    fun provideProductsApi(retrofit: RetrofitClient) = retrofit.provideProductsApi()
}
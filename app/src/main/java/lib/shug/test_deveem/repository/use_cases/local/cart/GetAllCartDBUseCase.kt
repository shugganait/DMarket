package lib.shug.test_deveem.repository.use_cases.local.cart

import lib.shug.test_deveem.repository.local.cart.LocalCartRepository
import javax.inject.Inject

class GetAllCartDBUseCase @Inject constructor(private val repository: LocalCartRepository) {

    fun getAllProducts() = repository.getAllCart()

}
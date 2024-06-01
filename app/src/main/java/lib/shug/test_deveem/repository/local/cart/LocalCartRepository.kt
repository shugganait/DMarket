package lib.shug.test_deveem.repository.local.cart

import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.local.entities.CartEntity

interface LocalCartRepository {
    fun getAllCart(): Flow<List<CartEntity>>
    fun deleteCartById(id: Int)
    fun insertCart(cart: CartEntity)
    fun deleteAll()
}
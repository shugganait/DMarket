package lib.shug.test_deveem.repository.local.cart

import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.local.Dao
import lib.shug.test_deveem.model.local.entities.CartEntity
import javax.inject.Inject

class LocalCartRepositoryImpl @Inject constructor(private val dao: Dao) :
    LocalCartRepository {
    override fun getAllCart(): Flow<List<CartEntity>> {
        return dao.getAllCart()
    }

    override fun deleteCartById(id: Int) {
        dao.deleteCartById(id)
    }

    override fun insertCart(cart: CartEntity) {
        dao.insertCart(cart)
    }

    override fun deleteAll() {
        dao.deleteAllCart()
    }
}
package lib.shug.test_deveem.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.model.local.entities.ProductEntity
import lib.shug.test_deveem.utils.Constants.CART_TABLE_DB
import lib.shug.test_deveem.utils.Constants.PRODUCTS_TABLE_DB

@Dao
interface Dao {
    //PRODUCTS
    @Query("SELECT * FROM $PRODUCTS_TABLE_DB")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM $PRODUCTS_TABLE_DB WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM $PRODUCTS_TABLE_DB WHERE id = :id")
    fun getProductById(id: Int): Flow<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(productEntity: ProductEntity)

    @Query("DELETE FROM $PRODUCTS_TABLE_DB")
    fun deleteAllProducts()

    //CART
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cartEntity: CartEntity)

    @Query("SELECT * FROM $CART_TABLE_DB")
    fun getAllCart(): Flow<List<CartEntity>>

    @Query("DELETE FROM $CART_TABLE_DB")
    fun deleteAllCart()

    @Query("DELETE FROM $CART_TABLE_DB WHERE id = :id")
    fun deleteCartById(id: Int)
}
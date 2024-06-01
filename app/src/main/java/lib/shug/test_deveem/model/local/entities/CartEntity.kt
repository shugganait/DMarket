package lib.shug.test_deveem.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import lib.shug.test_deveem.utils.Constants.CART_TABLE_DB

@Entity(tableName = CART_TABLE_DB)
data class CartEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
)

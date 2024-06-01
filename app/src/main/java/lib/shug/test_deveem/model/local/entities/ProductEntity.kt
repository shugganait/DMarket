package lib.shug.test_deveem.model.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import lib.shug.test_deveem.utils.Constants.PRODUCTS_TABLE_DB

@Entity(tableName = PRODUCTS_TABLE_DB)
data class ProductEntity(
    val category: String,
    val description: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Double,
    val ratingCount: Int,
    val title: String
)
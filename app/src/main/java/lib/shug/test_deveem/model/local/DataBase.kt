package lib.shug.test_deveem.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lib.shug.test_deveem.model.local.entities.CartEntity
import lib.shug.test_deveem.model.local.entities.ProductEntity

@Database(entities = [ProductEntity::class, CartEntity::class], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun dbDao(): Dao
}
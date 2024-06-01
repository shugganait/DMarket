package lib.shug.test_deveem.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lib.shug.test_deveem.model.local.Dao
import lib.shug.test_deveem.model.local.DataBase
import lib.shug.test_deveem.utils.Constants.ROOM_NAME_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context, DataBase::class.java, ROOM_NAME_DB
        ).addMigrations(MIGRATION_1_2) .build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `CartEntity` (`id` INTEGER NOT NULL, `productName` TEXT, PRIMARY KEY(`id`))")
        }
    }

    @Provides
    fun provideDao(db: DataBase): Dao {
        return db.dbDao()
    }
}
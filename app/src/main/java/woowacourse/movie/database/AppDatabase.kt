package woowacourse.movie.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Purchase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun purchaseDao(): PurchaseDao
}

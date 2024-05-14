package woowacourse.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.model.ReservationEntity

@Database(entities = [ReservationEntity::class], version = 1)
@TypeConverters(ReservationTypeConverters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun dao(): ReservationDao

    companion object {
        @Volatile
        private var instance: ReservationDatabase? = null

        fun getDatabase(context: Context): ReservationDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ReservationDatabase::class.java,
                    "reservation-database",
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}

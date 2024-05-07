package woowacourse.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.model.ReservationEntity

@Database(entities = [ReservationEntity::class], version = 8)
@TypeConverters(ReservationTypeConverters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun dao(): ReservationDao

    companion object {
        private var instance: ReservationDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ReservationDatabase? {
            if (instance == null) {
                synchronized(ReservationDatabase::class) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            ReservationDatabase::class.java,
                            "reservation-database",
                        ).fallbackToDestructiveMigration().build()
                }
            }
            return instance
        }
    }
}

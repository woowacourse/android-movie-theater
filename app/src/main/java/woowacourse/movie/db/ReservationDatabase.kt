package woowacourse.movie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReservationEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var instance: ReservationDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): ReservationDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, ReservationDatabase::class.java, "reservations")
                    .build()
                    .also { instance = it }
            }
        }
    }
}

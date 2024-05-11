package woowacourse.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Reservation::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var Instance: ReservationDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): ReservationDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    ReservationDatabase::class.java,
                    "reservation_database",
                ).build().also { Instance = it }
            }
        }
    }
}

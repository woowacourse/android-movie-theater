package woowacourse.movie.db.reservationhistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReservationHistory::class], version = 1)
@TypeConverters(ReservationHistoryConverter::class)
abstract class ReservationHistoryDatabase : RoomDatabase() {
    abstract fun reservationHistoryDao(): ReservationHistoryDao

    companion object {
        @Volatile
        private var instance: ReservationHistoryDatabase? = null
        private const val RESERVATION_HISTORY_DATABASE = "reservationHistoryDatabase"

        fun getInstance(context: Context): ReservationHistoryDatabase {
            var instance = instance

            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ReservationHistoryDatabase::class.java,
                        RESERVATION_HISTORY_DATABASE,
                    ).build()
            }

            return instance
        }
    }
}

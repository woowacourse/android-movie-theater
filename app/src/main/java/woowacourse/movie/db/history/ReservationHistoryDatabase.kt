package woowacourse.movie.db.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.model.ticket.ReservationTicket

@Database(
    entities = [
        ReservationTicket::class,
    ],
    version = 2,
)
@TypeConverters(ReservationHistoryConverters::class)
abstract class ReservationHistoryDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationHistoryDao

    companion object {
        private var instance: ReservationHistoryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ReservationHistoryDatabase {
            return instance
                ?: synchronized(ReservationHistoryDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        ReservationHistoryDatabase::class.java,
                        "reservationTicket",
                    ).build()
                }
        }
    }
}

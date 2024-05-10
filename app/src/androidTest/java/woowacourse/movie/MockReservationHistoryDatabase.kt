package woowacourse.movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.db.history.ReservationHistoryConverters
import woowacourse.movie.model.ticket.ReservationTicket

@Database(
    entities = [
        ReservationTicket::class,
    ],
    version = 1,
)
@TypeConverters(ReservationHistoryConverters::class)
abstract class MockReservationHistoryDatabase : RoomDatabase() {
    abstract fun reservationHistoryDao(): MockReservationHistoryDao

    companion object {
        private var instance: MockReservationHistoryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MockReservationHistoryDatabase {
            return instance
                ?: synchronized(MockReservationHistoryDatabase::class) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        MockReservationHistoryDatabase::class.java,
                        "mockReservationTicket",
                    ).build()
                }
        }
    }
}

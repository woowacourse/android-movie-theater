package woowacourse.movie.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ReservationHistory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationHistoryDao(): ReservationHistoryDao
}

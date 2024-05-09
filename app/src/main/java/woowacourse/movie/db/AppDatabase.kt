package woowacourse.movie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ReservationHistory::class], version = 1)
@TypeConverters(ReservationHistoryConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationHistoryDao(): ReservationHistoryDao
}

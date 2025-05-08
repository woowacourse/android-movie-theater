package woowacourse.movie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookingHistory::class], version = 1)
@TypeConverters(BookingHistoryConverters::class)
abstract class BookingHistoryDatabase : RoomDatabase() {
    abstract fun bookingHistoryDao(): BookingHistoryDao
}
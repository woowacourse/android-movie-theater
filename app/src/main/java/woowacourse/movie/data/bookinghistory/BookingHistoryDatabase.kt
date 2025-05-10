package woowacourse.movie.data.bookinghistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookingHistory::class], version = 1)
@TypeConverters(BookingHistoryConverters::class)
abstract class BookingHistoryDatabase : RoomDatabase() {
    abstract fun bookingHistoryDao(): BookingHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: BookingHistoryDatabase? = null

        fun getDatabase(context: Context): BookingHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    BookingHistoryDatabase::class.java,
                    "booking_histories",
                ).build().also { INSTANCE = it }
            }
        }
    }
}

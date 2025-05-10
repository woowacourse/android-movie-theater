package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookingHistoryDetails::class], version = 1)
abstract class BookingHistoryDetailsDatabase : RoomDatabase() {
    abstract fun bookingHistoryDetailsDao(): BookingHistoryDetailsDao

    companion object {
        @Volatile
        private var instance: BookingHistoryDetailsDatabase? = null

        fun getDatabase(context: Context): BookingHistoryDetailsDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(
                        context.applicationContext,
                        BookingHistoryDetailsDatabase::class.java,
                        "booking_history_db",
                    ).build()
                    .also { instance = it }
            }
    }
}

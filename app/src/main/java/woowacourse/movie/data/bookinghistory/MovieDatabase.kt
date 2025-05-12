package woowacourse.movie.data.bookinghistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.MovieApplication

@Database(entities = [BookingHistory::class], version = 1)
@TypeConverters(BookingHistoryConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun bookingHistoryDao(): BookingHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context = MovieApplication.instance): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "booking_histories",
                ).build().also { INSTANCE = it }
            }
        }
    }
}

package woowacourse.movie.dao.bookingStatus

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.dao.bookingStatus.BookingStatusEntity

@Database(entities = [BookingStatusEntity::class], version = 1)
abstract class BookingStatusDatabase : RoomDatabase() {
    abstract fun BookingStatusDao(): BookingStatusDao

    companion object {
        private var DATABASE: BookingStatusDatabase? = null

        fun database(context: Context): BookingStatusDao {
            val database = DATABASE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookingStatusDatabase::class.java,
                    "booking_status"
                ).build().also { DATABASE = it }

            return database.BookingStatusDao()
        }
    }
}

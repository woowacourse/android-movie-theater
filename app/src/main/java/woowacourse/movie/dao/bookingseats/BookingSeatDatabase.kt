package woowacourse.movie.dao.bookingseats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookingSeatEntity::class], version = 2)
abstract class BookingSeatDatabase : RoomDatabase() {
    abstract fun BookingSeatDao(): BookingSeatDao

    companion object {
        private var DATABASE: BookingSeatDatabase? = null

        fun database(context: Context): BookingSeatDao {
            val database = DATABASE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookingSeatDatabase::class.java,
                    "booking_seat"
                ).build().also { DATABASE = it }

            return database.BookingSeatDao()
        }
    }
}

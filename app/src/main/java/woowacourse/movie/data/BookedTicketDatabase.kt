package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookedTicketEntity::class], version = 1)
@TypeConverters(BookedTicketConverter::class)
abstract class BookedTicketDatabase : RoomDatabase() {
    abstract fun bookedTicketDao(): BookedTicketDao

    companion object {
        private const val DATABASE_NAME = "booked-tickets-db"

        @Volatile
        private var INSTANCE: BookedTicketDatabase? = null

        fun getInstance(context: Context): BookedTicketDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BookedTicketDatabase::class.java,
                    DATABASE_NAME,
                ).build().also { INSTANCE = it }
            }
        }

        fun close() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}

package woowacourse.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Ticket::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var database: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "ticket",
                    ).fallbackToDestructiveMigration()
                        .build()
                database = instance
                instance
            }
        }
    }
}

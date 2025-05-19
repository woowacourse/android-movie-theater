package woowacourse.movie.data.ticket

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TicketEntity::class], version = 1)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var database: TicketDatabase? = null

        fun database(context: Context): TicketDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        TicketDatabase::class.java,
                        "ticket_database",
                    ).build()
                database = instance
                instance
            }
        }
    }
}

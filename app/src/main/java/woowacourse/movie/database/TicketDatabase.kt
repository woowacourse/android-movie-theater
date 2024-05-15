package woowacourse.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.ticket.model.TicketEntity

@Database(entities = [TicketEntity::class], version = 1)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        var INSTANCE: TicketDatabase? = null

        fun getDatabase(context: Context): TicketDatabase {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TicketDatabase::class.java,
                "alsong.db"
            ).build()
            return INSTANCE ?: synchronized(this) { instance }
        }
    }
}

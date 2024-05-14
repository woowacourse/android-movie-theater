package woowacourse.movie.db.ticket

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.db.Converters

@Database(entities = [TicketEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        fun initialize(context: Context): TicketDatabase {
            return Room.databaseBuilder(
                context,
                TicketDatabase::class.java,
                "reservation_history",
            ).build()
        }
    }
}

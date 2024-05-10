package woowacourse.movie.db.ticket

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Ticket::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}

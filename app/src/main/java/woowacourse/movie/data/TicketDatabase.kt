package woowacourse.movie.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TicketEntity::class], version = 1)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}

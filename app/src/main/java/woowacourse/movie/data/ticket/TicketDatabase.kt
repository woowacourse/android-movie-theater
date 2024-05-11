package woowacourse.movie.data.ticket

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.Volatile

@Database(entities = [Ticket::class], version = 1)
@TypeConverters(TicketConverters::class)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var instance: TicketDatabase? = null

        fun instance(context: Context): TicketDatabase {
            return instance ?: run {
                synchronized(this) {
                    val newInstance =
                        Room.databaseBuilder(context, TicketDatabase::class.java, "ticket").build()
                    instance = newInstance
                    newInstance
                }
            }
        }
    }
}

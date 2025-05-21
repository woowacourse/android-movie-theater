package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.DateTimeConverter
import woowacourse.movie.data.converter.SeatsConverter

@TypeConverters(DateTimeConverter::class, SeatsConverter::class)
@Database(entities = [TicketEntity::class], version = 1)
abstract class TicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        private const val TICKET_DATABASE = "ticket_database"

        @Volatile
        private var instance: TicketDatabase? = null

        fun getDataBase(context: Context): TicketDatabase {
            return instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        TicketDatabase::class.java,
                        TICKET_DATABASE,
                    ).build()
                this.instance = instance

                instance
            }
        }
    }
}

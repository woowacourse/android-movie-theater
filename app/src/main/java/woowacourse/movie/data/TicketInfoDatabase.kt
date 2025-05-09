package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TicketInfo::class], version = 1)
abstract class TicketInfoDatabase : RoomDatabase() {
    abstract fun ticketInfoDao(): TicketInfoDao

    companion object {
        @Suppress("ktlint:standard:property-naming")
        @Volatile
        private var INSTANCE: TicketInfoDatabase? = null

        fun getDatabase(context: Context): TicketInfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        TicketInfoDatabase::class.java,
                        "ticketInfo",
                    ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

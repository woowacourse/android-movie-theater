package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TicketInfo::class], version = 1)
abstract class TicketInfoDatabase : RoomDatabase() {
    abstract fun ticketInfoDao(): TicketInfoDao

    companion object {
        private const val DATABASE_NAME = "ticketInfo"

        @Volatile
        private var database: TicketInfoDatabase? = null

        fun getDatabase(context: Context): TicketInfoDatabase {
            return database ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        TicketInfoDatabase::class.java,
                        DATABASE_NAME,
                    ).build()
                database = instance
                instance
            }
        }
    }
}

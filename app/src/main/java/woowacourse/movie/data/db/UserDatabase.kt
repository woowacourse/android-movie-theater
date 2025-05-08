package woowacourse.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.db.converter.DateTimeConverter
import woowacourse.movie.data.db.converter.SeatTypeConverters

@Database(entities = [TicketEntity::class], version = 1)
@TypeConverters(
    DateTimeConverter::class,
    SeatTypeConverters::class,
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database",
                    )
                        .fallbackToDestructiveMigration(true)
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

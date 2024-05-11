package woowacourse.movie.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserTicket::class], version = 1)
@TypeConverters(UserTicketTypeConverters::class)
abstract class UserTicketDatabase : RoomDatabase() {
    abstract fun userTicketDao(): UserTicketDao

    companion object {
        private const val DATABASE_NAME = "user_ticket_db"
        private const val ERROR_DATABASE = "데이터베이스가 초기화 되지 않았습니다."
        private var database: UserTicketDatabase? = null

        fun database(): UserTicketDatabase {
            return database ?: throw IllegalStateException(ERROR_DATABASE)
        }

        fun initialize(context: Context) {
            if (database == null) {
                database =
                    Room.databaseBuilder(
                        context.applicationContext,
                        UserTicketDatabase::class.java,
                        DATABASE_NAME,
                    ).build()
            }
        }
    }
}

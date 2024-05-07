package woowacourse.movie.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserTicket::class], version = 1)
@TypeConverters(UserTicketTypeConverters::class)
abstract class UserTicketDatabase : RoomDatabase() {
    abstract fun userTicketDao(): UserTicketDao
}

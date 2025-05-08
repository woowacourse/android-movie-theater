package woowacourse.movie.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieTicket::class], version = 1)
abstract class MovieTicketDatabase : RoomDatabase() {
    abstract fun movieTicketDao(): MovieTicketDao
}

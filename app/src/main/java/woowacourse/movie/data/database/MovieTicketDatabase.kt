package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.MovieTicketDao
import woowacourse.movie.domain.model.movieticket.MovieTicket

@Database(entities = [MovieTicket::class], version = 1)
abstract class MovieTicketDatabase : RoomDatabase() {
    abstract fun movieTicketDao(): MovieTicketDao
}

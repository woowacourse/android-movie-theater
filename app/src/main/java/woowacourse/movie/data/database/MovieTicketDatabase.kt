package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.MovieTicketDao
import woowacourse.movie.data.model.MovieTicketEntity

@Database(entities = [MovieTicketEntity::class], version = 1)
abstract class MovieTicketDatabase : RoomDatabase() {
    abstract fun movieTicketDao(): MovieTicketDao
}

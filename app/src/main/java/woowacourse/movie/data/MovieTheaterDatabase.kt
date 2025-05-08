package woowacourse.movie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.TicketConverter
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.TicketEntity

@Database(
    entities = [
        TicketEntity::class,
    ],
    version = 1,
)
@TypeConverters(TicketConverter::class)
abstract class MovieTheaterDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        lateinit var db: MovieTheaterDatabase
    }
}

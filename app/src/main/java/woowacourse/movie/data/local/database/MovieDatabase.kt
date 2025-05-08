package woowacourse.movie.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.local.dao.TicketDao
import woowacourse.movie.data.local.entity.TicketEntity

@Database(entities = [TicketEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}

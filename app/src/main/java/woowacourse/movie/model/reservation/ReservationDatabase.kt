package woowacourse.movie.model.reservation

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.model.ticket.MovieTicket

@Database(
    entities = [
        MovieTicket::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao
}

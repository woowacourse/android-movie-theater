package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.Reservation

@Database(entities = [Reservation::class], version = 1)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao
}

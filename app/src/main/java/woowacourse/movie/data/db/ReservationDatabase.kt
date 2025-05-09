package woowacourse.movie.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity

@Database(
    entities = [
        ReservationInfoEntity::class,
        SeatEntity::class,
        MovieEntity::class,
        TheaterEntity::class,
    ],
    version = 9,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao
}

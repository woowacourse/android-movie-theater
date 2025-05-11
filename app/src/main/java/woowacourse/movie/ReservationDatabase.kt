package woowacourse.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ReservationEntity::class],
    version = 1,
)
@TypeConverters(ReservationConverters::class)
abstract class ReservationDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao
}

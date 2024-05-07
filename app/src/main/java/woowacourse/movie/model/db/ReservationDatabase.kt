package woowacourse.movie.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Reservation::class], version = 1)
abstract class ReservationDatabase : RoomDatabase()

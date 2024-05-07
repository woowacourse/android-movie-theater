package woowacourse.movie.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Reservation::class], version = 1)
@TypeConverters(ReservationTypeConverters::class)
abstract class ReservationDatabase : RoomDatabase()

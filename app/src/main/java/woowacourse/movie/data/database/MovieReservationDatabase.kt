package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.LocalDateTimeConverters
import woowacourse.movie.data.converter.ReservationSeatsConverters
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.entity.Movie
import woowacourse.movie.data.entity.MovieReservation
import woowacourse.movie.data.entity.MovieTheater


@Database(entities = [MovieReservation::class, MovieTheater::class, Movie::class], version = 1)
@TypeConverters(value = [ReservationSeatsConverters::class, LocalDateTimeConverters::class, ReservationSeatsConverters::class])
abstract class MovieReservationDatabase:RoomDatabase() {
    abstract fun movieReservationDao(): MovieReservationDao
}

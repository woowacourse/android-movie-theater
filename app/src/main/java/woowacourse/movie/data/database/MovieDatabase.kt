package woowacourse.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.LocalDateTimeConverters
import woowacourse.movie.data.converter.ReservationSeatsConverters
import woowacourse.movie.data.converter.ScreenDateTimeConverters
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.dao.ScreeningMovieDao
import woowacourse.movie.data.entity.Movie
import woowacourse.movie.data.entity.MovieReservation
import woowacourse.movie.data.entity.MovieTheater
import woowacourse.movie.data.entity.ScreeningMovie

@Database(
    entities = [MovieReservation::class, MovieTheater::class, Movie::class, ScreeningMovie::class],
    version = 1,
)
@TypeConverters(value = [ReservationSeatsConverters::class, LocalDateTimeConverters::class, ScreenDateTimeConverters::class])
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun movieTheaterDao(): MovieTheaterDao

    abstract fun movieReservationDao(): MovieReservationDao

    abstract fun screeningMovieDao(): ScreeningMovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun database(context: Context): MovieDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    buildDatabase(context)
                instance = newInstance
                return newInstance
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie",
            ).build()
    }
}

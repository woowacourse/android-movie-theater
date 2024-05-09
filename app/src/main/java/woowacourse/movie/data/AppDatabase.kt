package woowacourse.movie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.movie.MovieDao
import woowacourse.movie.data.movie.MovieDto
import woowacourse.movie.data.screening.ScreeningDao
import woowacourse.movie.data.screening.ScreeningDto
import woowacourse.movie.data.theater.TheaterDao
import woowacourse.movie.data.theater.TheaterDto

@Database(
    entities = [
        MovieDto::class,
        TheaterDto::class,
        ScreeningDto::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun theaterDao(): TheaterDao

    abstract fun screeningDao(): ScreeningDao
}

package woowacourse.movie.data

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.movie.MovieDao
import woowacourse.movie.data.movie.MovieDto
import woowacourse.movie.data.theater.TheaterDao
import woowacourse.movie.data.theater.TheaterDto

@Database(
    entities = [
        MovieDto::class,
        TheaterDto::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun theaterDao(): TheaterDao
}

package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.entity.MovieTheater

@Database(entities = [MovieTheater::class], version = 1)
abstract class MovieTheaterDatabase : RoomDatabase() {
    abstract fun movieTheaterDao(): MovieTheaterDao
}

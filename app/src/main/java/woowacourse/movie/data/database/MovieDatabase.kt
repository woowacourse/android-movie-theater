package woowacourse.movie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.LocalDateTimeConverters
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.entity.Movie

@Database(entities = [Movie::class], version = 2)
@TypeConverters(LocalDateTimeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

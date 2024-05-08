package woowacourse.movie.data
import androidx.room.Database
import androidx.room.RoomDatabase
import woowacourse.movie.data.movie.MovieDao
import woowacourse.movie.data.movie.MovieDto

@Database(entities = [MovieDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}

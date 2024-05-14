package woowacourse.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.entity.ReservationEntity
import woowacourse.movie.data.entity.SeatEntity

@Database(entities = [ReservationEntity::class, SeatEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        private const val DATABASE_NAME = "movie.db"

        @Volatile
        private var instance: MovieDatabase? = null

        fun instance(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    DATABASE_NAME,
                ).build().also { instance = it }
            }
        }
    }
}

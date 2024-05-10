package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.data.movie.MovieDao
import woowacourse.movie.data.movie.MovieDto
import woowacourse.movie.data.reservationref.ReservationRefDao
import woowacourse.movie.data.reservationref.ReservationRefDto
import woowacourse.movie.data.screeningref.ScreeningRefDao
import woowacourse.movie.data.screeningref.ScreeningRefDto
import woowacourse.movie.data.theater.TheaterDao
import woowacourse.movie.data.theater.TheaterDto

@Database(
    entities = [
        MovieDto::class,
        TheaterDto::class,
        ScreeningRefDto::class,
        ReservationRefDto::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun theaterDao(): TheaterDao

    abstract fun screeningDao(): ScreeningRefDao

    abstract fun reservationDao(): ReservationRefDao

    companion object {
        @Volatile
        private var dbInstance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return dbInstance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "movie_database",
                    ).fallbackToDestructiveMigration().build()
                dbInstance = instance
                instance
            }
        }
    }
}

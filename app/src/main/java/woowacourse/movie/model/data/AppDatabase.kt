package woowacourse.movie.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ScreeningRef
import woowacourse.movie.model.Theater
import woowacourse.movie.model.data.movie.MovieDao
import woowacourse.movie.model.data.movie.MovieDto
import woowacourse.movie.model.data.movie.toDto
import woowacourse.movie.model.data.reservationref.ReservationRefDao
import woowacourse.movie.model.data.reservationref.ReservationRefDto
import woowacourse.movie.model.data.screeningref.ScreeningRefDao
import woowacourse.movie.model.data.screeningref.ScreeningRefDto
import woowacourse.movie.model.data.screeningref.toDto
import woowacourse.movie.model.data.theater.TheaterDao
import woowacourse.movie.model.data.theater.TheaterDto
import woowacourse.movie.model.data.theater.toDto

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
                    ).addCallback(
                        object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                initDb(context)
                            }
                        },
                    ).fallbackToDestructiveMigration().build()
                dbInstance = instance
                instance
            }
        }

        fun initDb(context: Context) {
            val db = getDatabase(context)
            Thread {
                with(db.movieDao()) {
                    insert(Movie.STUB_A.toDto())
                    insert(Movie.STUB_B.toDto())
                    insert(Movie.STUB_C.toDto())
                }
                with(db.theaterDao()) {
                    insert(Theater.STUB_A.toDto())
                    insert(Theater.STUB_B.toDto())
                    insert(Theater.STUB_C.toDto())
                }
                with(db.screeningDao()) {
                    insertAll(ScreeningRef.getStubList().map { it.toDto() })
                }
            }.start()
        }
    }
}

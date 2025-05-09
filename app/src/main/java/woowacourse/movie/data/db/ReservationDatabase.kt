package woowacourse.movie.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity

@Database(
    entities = [
        ReservationInfoEntity::class,
        SeatEntity::class,
        MovieEntity::class,
        TheaterEntity::class,
    ],
    version = 9,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_data",
                        ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

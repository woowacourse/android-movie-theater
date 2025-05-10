package woowacourse.movie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.database.typeconverter.DateConverter
import woowacourse.movie.database.typeconverter.SeatsConverter
import woowacourse.movie.model.reservation.ReservationInfo
import woowacourse.movie.model.reservation.ReservationInfoDao

@Database(entities = [ReservationInfo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, SeatsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reservationInfoDao(): ReservationInfoDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "movie_database",
                        ).build()
                INSTANCE = instance
                instance
            }
    }
}

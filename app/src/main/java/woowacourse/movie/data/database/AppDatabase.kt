package woowacourse.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.ScreeningPeriodConverter
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(ScreeningPeriodConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var iNSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            iNSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database",
                        ).build()

                iNSTANCE = instance
                instance
            }
    }
}

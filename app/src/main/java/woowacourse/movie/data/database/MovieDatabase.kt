package woowacourse.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.MovieConverter
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.TicketEntity

@Database(entities = [TicketEntity::class], version = 2)
@TypeConverters(MovieConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun TicketDao(): TicketDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return this.instance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movie",
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                this.instance = instance
                instance
            }
        }
    }
}

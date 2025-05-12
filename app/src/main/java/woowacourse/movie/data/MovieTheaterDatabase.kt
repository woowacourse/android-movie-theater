package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.TicketConverter
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.TicketEntity

@Database(
    entities = [
        TicketEntity::class,
    ],
    version = 1,
)
@TypeConverters(TicketConverter::class)
abstract class MovieTheaterDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        private const val DATABASE_NAME = "movie_theater_db"

        @Volatile
        private var database: MovieTheaterDatabase? = null

        fun db(context: Context): MovieTheaterDatabase {
            var instance = database
            instance
                ?: run {
                    synchronized(this) {
                        instance = database
                        instance
                            ?: run {
                                instance =
                                    Room.databaseBuilder(
                                        context.applicationContext,
                                        MovieTheaterDatabase::class.java,
                                        DATABASE_NAME,
                                    )
                                        .allowMainThreadQueries()
                                        .build()
                                database = instance
                            }
                    }
                }
            return instance!!
        }
    }
}

package woowacourse.movie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.converter.TicketConverter
import woowacourse.movie.data.dao.CinemaDao
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.CinemaEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TicketEntity

@Database(
    entities = [
        TicketEntity::class,
        CinemaEntity::class,
        SeatEntity::class,
    ],
    version = 1,
)
@TypeConverters(TicketConverter::class)
abstract class MovieTheaterDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    abstract fun cinemaDao(): CinemaDao

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

package woowacourse.movie.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.movie.MovieContentEntity
import woowacourse.movie.data.database.theater.TheaterDao
import woowacourse.movie.data.database.theater.TheaterEntity
import woowacourse.movie.data.database.ticket.TicketContract.MOVIE_DATABASE
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.data.database.util.LongListConverter
import woowacourse.movie.data.database.util.StringListConverter

@TypeConverters(
    StringListConverter::class,
    LongListConverter::class,
)
@Database(
    entities = [
        TicketEntity::class,
        TheaterEntity::class,
        MovieContentEntity::class,
    ],
    version = 8,
    autoMigrations = [AutoMigration(6, 7), AutoMigration(7, 8)],
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    abstract fun theaterDao(): TheaterDao

    abstract fun movieContentDao(): MovieContentDao

    companion object {
        @Volatile
        private var synchronizedInstance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return synchronizedInstance ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        MOVIE_DATABASE,
                    )
                        .createFromAsset("database/movie_contents.db")
                        .build()
                synchronizedInstance = instance
                instance
            }
        }
    }
}

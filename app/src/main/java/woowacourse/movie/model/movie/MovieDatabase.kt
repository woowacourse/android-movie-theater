package woowacourse.movie.model.movie

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.model.movie.TicketContract.MOVIE_DATABASE

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
    version = 7,
    autoMigrations = [AutoMigration(6, 7)],
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    abstract fun theaterDao(): TheaterDao

    abstract fun movieContentDao(): MovieContentDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        MOVIE_DATABASE,
                    )
                        .createFromAsset("database/movie_contents.db")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

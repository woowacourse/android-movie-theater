package woowacourse.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.ticket.TicketDao
import woowacourse.movie.data.ticket.TicketEntity

@Database(entities = [TicketEntity::class], version = 1)
@TypeConverters(MovieConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val ticketDao: TicketDao

    companion object {
        private const val MOVIE_DATABASE_NAME = "movie-db"

        @Volatile
        private var movieDatabase: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase =
            movieDatabase ?: synchronized(this) {
                movieDatabase ?: Room
                    .databaseBuilder(
                        context,
                        MovieDatabase::class.java,
                        MOVIE_DATABASE_NAME,
                    ).build()
                    .also {
                        movieDatabase = it
                    }
            }
    }
}

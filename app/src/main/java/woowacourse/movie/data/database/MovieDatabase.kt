package woowacourse.movie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.entity.TicketEntity

@Database(entities = [TicketEntity::class], version = 2, exportSchema = false)
@TypeConverters(MovieConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        private var movieDatabase: MovieDatabase? = null
        private const val MOVIE_DATABASE_NAME = "movie-db"

        fun getDatabase(applicationContext: Context): MovieDatabase =
            movieDatabase ?: Room
                .databaseBuilder(applicationContext, MovieDatabase::class.java, MOVIE_DATABASE_NAME)
                .build()
    }
}

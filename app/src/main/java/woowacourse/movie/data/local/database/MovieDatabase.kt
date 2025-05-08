package woowacourse.movie.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.data.local.dao.TicketDao
import woowacourse.movie.data.local.entity.TicketEntity

@Database(entities = [TicketEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        private var instance: MovieDatabase? = null

        fun getMovieDatabase(context: Context): MovieDatabase {
            return instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context): MovieDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                "movie_database",
            ).build()
        }
    }
}

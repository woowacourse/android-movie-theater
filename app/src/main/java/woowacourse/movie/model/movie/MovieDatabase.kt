package woowacourse.movie.model.movie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.TicketContract.MOVIE_DATABASE

@Database(
    entities = [TicketEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    companion object {
        fun getDatabase(context: Context): MovieDatabase {
            return Room
                .databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    MOVIE_DATABASE
                )
                .build()
        }
    }
}

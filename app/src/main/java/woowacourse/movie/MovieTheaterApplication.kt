package woowacourse.movie

import android.app.Application
import androidx.room.Room
import woowacourse.movie.data.MovieTheaterDatabase

class MovieTheaterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MovieTheaterDatabase.db =
            Room.databaseBuilder(
                applicationContext,
                MovieTheaterDatabase::class.java,
                "movie_theater_db",
            ).build()
    }
}

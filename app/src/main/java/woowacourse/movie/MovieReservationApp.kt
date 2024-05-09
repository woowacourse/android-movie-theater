package woowacourse.movie

import android.app.Application
import woowacourse.movie.data.DefaultMovieRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.db.MovieDatabase

class MovieReservationApp : Application() {
    val movieRepository: MovieRepository by lazy {
        val db = MovieDatabase.instance(applicationContext)
        DefaultMovieRepository(db.movieDao())
    }
}
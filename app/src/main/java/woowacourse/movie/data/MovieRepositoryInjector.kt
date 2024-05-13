package woowacourse.movie.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.db.MovieDatabase

class MovieRepositoryInjector(private val context: Context) {
    private var movieRepository: MovieRepository? = null

    fun movieRepository(): MovieRepository = movieRepository ?: synchronized(this) {
        movieRepository ?: DefaultMovieRepository(movieDao()).also { movieRepository = it }
    }

    private fun movieDao(): MovieDao = MovieDatabase.instance(context).movieDao()

    @VisibleForTesting
    fun setRepository(movieRepository: MovieRepository) {
        this.movieRepository = movieRepository
    }

    @VisibleForTesting
    fun clear() {
        movieRepository = null
    }

    companion object {
        @Volatile
        private var instance: MovieRepositoryInjector? = null

        fun instance(context: Context): MovieRepositoryInjector {
            return instance ?: synchronized(this) {
                instance ?: MovieRepositoryInjector(context).also { instance = it }
            }
        }
    }
}

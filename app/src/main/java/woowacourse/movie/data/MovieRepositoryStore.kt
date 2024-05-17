package woowacourse.movie.data

import android.content.Context
import androidx.annotation.VisibleForTesting
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.db.MovieDatabase

class MovieRepositoryStore(private val context: Context) {
    private var movieRepository: MovieRepository? = null
    private var movieDao: MovieDao? = null

    fun movieRepository(): MovieRepository {
        return movieRepository ?: DefaultMovieRepository(movieDao())
    }

    private fun movieDao(): MovieDao {
        return movieDao ?: MovieDatabase.instance(context).movieDao().also { movieDao = it }
    }

    @VisibleForTesting
    fun setRepository(movieRepository: MovieRepository) {
        this.movieRepository = movieRepository
    }

    @VisibleForTesting
    fun setMovieDao(movieDao: MovieDao) {
        this.movieDao = movieDao
    }

    @VisibleForTesting
    fun clear() {
        movieRepository = null
        movieDao = null
    }

    companion object {
        @Volatile
        private var instance: MovieRepositoryStore? = null

        fun instance(context: Context): MovieRepositoryStore {
            return instance ?: synchronized(this) {
                instance ?: MovieRepositoryStore(context).also { instance = it }
            }
        }
    }
}

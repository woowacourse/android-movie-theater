package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.MovieDataSource
import woowacourse.movie.data.database.MovieDao
import woowacourse.movie.domain.Movie

class MovieRepository(movieDao: MovieDao) {
    private val movieDataSource: DataSource<Movie> = MovieDataSource(movieDao)

    fun requestMovies(): List<Movie> {
        return movieDataSource.value
    }
}

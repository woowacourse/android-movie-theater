package woowacourse.movie.common.repository

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.dataSource.DataSource
import woowacourse.movie.dataSource.MovieDataSource
import woowacourse.movie.domain.Movie

class MovieRepository(movieDao: MovieDao) {
    private val movieDataSource: DataSource<Movie> = MovieDataSource(movieDao)

    fun requestMovies(): List<Movie> {
        return movieDataSource.value
    }
}

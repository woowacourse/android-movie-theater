package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.MovieDataSource

class MovieRepository {
    private val movieDataSource: DataSource<Movie> = MovieDataSource()

    fun requestMovies(): List<Movie> {
        return movieDataSource.value
    }
}

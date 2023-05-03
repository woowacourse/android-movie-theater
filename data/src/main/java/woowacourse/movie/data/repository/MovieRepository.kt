package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.MovieDataSource
import woowacourse.movie.domain.Movie

class MovieRepository {
    private val movieDataSource: DataSource<Movie> = MovieDataSource()

    fun requestMovies(): List<Movie> {
        return movieDataSource.value
    }
}

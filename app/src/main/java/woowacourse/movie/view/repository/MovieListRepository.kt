package woowacourse.movie.view.repository

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.dataSource.DataSource
import woowacourse.movie.domain.dataSource.MovieDataSource

class MovieListRepository {
    private val movieDataSource: DataSource<Movie> = MovieDataSource()

    fun requestMovie(): List<Movie> {
        return movieDataSource.value
    }
}

package woowacourse.movie.domain.repository

import woowacourse.movie.domain.dataSource.MovieDataSource
import woowacourse.movie.domain.model.Movie

class MovieRepository(private val dataSource: MovieDataSource) {
    fun getData(): List<Movie> {
        return dataSource.getData()
    }

    fun addData(data: Movie) {
        dataSource.addData(data)
    }
}

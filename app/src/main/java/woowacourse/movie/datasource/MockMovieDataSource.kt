package woowacourse.movie.datasource

import woowacourse.movie.domain.dataSource.MovieDataSource
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieMock

class MockMovieDataSource : MovieDataSource {
    override fun getData(): List<Movie> {
        return data
    }

    override fun addData(data: Movie) {
        Companion.data.add(data)
    }

    companion object {
        private val data: MutableList<Movie> =
            List(2500) { MovieMock.createMovies() }.flatten().toMutableList()
    }
}

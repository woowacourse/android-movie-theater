package woowacourse.movie.datasource

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieMock
import woowacourse.movie.domain.dataSource.MovieDataSource

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

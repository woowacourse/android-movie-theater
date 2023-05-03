package woowacourse.data.movie

import woowacourse.data.movie.MovieMapper.toMovie
import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.MovieRepository

class MovieRepositoryImpl : MovieRepository {
    override fun getMovies(): List<Movie> {
        return MovieDatabase.movies.map { it.toMovie() }
    }

    override fun getMovie(movieId: Long): Movie? {
        return MovieDatabase.selectMovie(movieId)?.toMovie()
    }
}

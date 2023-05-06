package woowacourse.data.movie

import woowacourse.data.movie.MovieMapper.toMovie
import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.MovieRepository

class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
    override fun getMovies(): List<Movie> {
        return movieDataSource.getMovieEntities().map { it.toMovie() }
    }

    override fun getMovie(movieId: Long): Movie? {
        return movieDataSource.getMovieEntity(movieId)?.toMovie()
    }
}

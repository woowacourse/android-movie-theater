package woowacourse.movie.data.movie

import woowacourse.movie.model.Movie
import woowacourse.movie.repository.MovieRepository

class MovieRepositoryImpl(private val movieDao: MovieDao) : MovieRepository {
    override fun movieById(id: Long): Movie? = movieDao.getById(id)?.toMovie()

    override fun movies(): List<Movie> = movieDao.getAll().map { it.toMovie() }
}

package woowacourse.movie.domain

import woowacourse.movie.domain.repository.MovieRepository

class MovieProvider {
    private val movieRepository: MovieRepository = MovieRepository()

    fun requestMovies(): List<Movie> =
        movieRepository.requestMovies()
}

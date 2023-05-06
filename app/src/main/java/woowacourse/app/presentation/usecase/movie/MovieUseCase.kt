package woowacourse.app.presentation.usecase.movie

import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.MovieRepository

class MovieUseCase(private val movieRepository: MovieRepository) {

    fun getMovie(movieId: Long): Movie? {
        return movieRepository.getMovie(movieId)
    }
}

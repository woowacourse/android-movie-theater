package woowacourse.movie.presentation.repository

import woowacourse.movie.domain.model.Movie

interface MovieRepository {
    fun createMovieList(): List<Movie>

    fun findMovieById(movieId: Int): Movie
}

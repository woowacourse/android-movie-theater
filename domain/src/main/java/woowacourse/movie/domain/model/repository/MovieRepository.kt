package woowacourse.movie.domain.model.repository

import woowacourse.movie.domain.model.movie.Movie

interface MovieRepository {
    fun getMovies(size: Int): List<Movie>
}

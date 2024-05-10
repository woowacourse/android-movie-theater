package woowacourse.movie.repository

import woowacourse.movie.model.Movie

interface MovieRepository {
    fun movieById(id: Long): Movie?

    fun movies(): List<Movie>
}

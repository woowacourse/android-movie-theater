package woowacourse.movie.domain.repository

import woowacourse.movie.domain.movie.Movie

interface MovieRepository {

    fun findAll(): List<Movie>
}

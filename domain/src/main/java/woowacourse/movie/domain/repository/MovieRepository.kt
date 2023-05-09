package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Movie

interface MovieRepository {

    fun findAll(): List<Movie>

    fun findById(id: Int): Movie?
}

package woowacourse.movie.domain.repository

import woowacourse.movie.domain.theater.Theater

interface TheaterRepository {
    fun findAll(): List<Theater>
}

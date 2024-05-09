package woowacourse.movie.repository

import woowacourse.movie.model.Theater

interface TheaterRepository {
    fun theaterById(theaterId: Long): Theater?
}

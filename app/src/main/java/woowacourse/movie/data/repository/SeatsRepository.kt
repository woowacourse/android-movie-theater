package woowacourse.movie.data.repository

import woowacourse.movie.domain.model.Seats

interface SeatsRepository {
    fun findById(screenId: Int): Seats
}

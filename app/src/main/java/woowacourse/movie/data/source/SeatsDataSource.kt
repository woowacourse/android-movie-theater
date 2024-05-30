package woowacourse.movie.data.source

import woowacourse.movie.domain.model.Seats

interface SeatsDataSource {
    fun findById(screenId: Int): Seats
}

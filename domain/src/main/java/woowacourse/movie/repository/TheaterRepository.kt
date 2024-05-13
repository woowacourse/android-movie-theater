package woowacourse.movie.repository

import woowacourse.movie.model.Theater

interface TheaterRepository {
    fun theaterById(theaterId: Long): Theater?

    fun theatersById(theaterIds: List<Long>): List<Theater>
}

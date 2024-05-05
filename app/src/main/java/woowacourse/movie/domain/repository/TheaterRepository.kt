package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.TheaterCount

interface TheaterRepository {
    fun findTheaterCount(id: Int): Result<List<TheaterCount>>

    fun findTheaterNameById(theaterId: Int): Result<String>
}

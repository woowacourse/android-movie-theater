package woowacourse.movie.repository

import woowacourse.movie.model.Theater

interface TheaterListRepository {
    val list: List<Theater>

    fun find(movieId: Long): List<Theater>

    fun findTheaterNameWithId(theaterId: Long): String
}

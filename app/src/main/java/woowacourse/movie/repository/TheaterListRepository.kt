package woowacourse.movie.repository

import woowacourse.movie.model.Theater

interface TheaterListRepository {
    val list: List<Theater>

    fun findTheaterList(movieId: Long): List<Theater>

    fun findTheaterOrNull(theaterId: Long): Theater?
}

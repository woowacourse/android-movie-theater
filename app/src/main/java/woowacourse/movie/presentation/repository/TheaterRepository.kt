package woowacourse.movie.presentation.repository

import woowacourse.movie.domain.model.Theater

interface TheaterRepository {
    fun theaters(): List<Theater>

    fun screenTimesCount(theaterId: Int, movieId: Int): Int

    fun theatersInfo(movieId: Int): List<Pair<Theater, Int>>
}

package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.SeatBoard

interface ScreenRepository {
    fun load(): List<ScreenView>

    fun loadSeatBoard(id: Int): Result<SeatBoard>

    fun findByScreenId(
        theaterId: Int,
        movieId: Int,
    ): Result<Screen>
}

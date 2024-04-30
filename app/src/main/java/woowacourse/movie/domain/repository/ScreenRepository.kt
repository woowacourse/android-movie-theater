package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.TheaterCount

interface ScreenRepository {
    fun load(): List<ScreenView>

    fun loadSeatBoard(id: Int): Result<SeatBoard>

    fun findByScreenId(
        theaterId: Int,
        movieId: Int,
    ): Result<Screen>

    fun findTheaterCount(id: Int): Result<List<TheaterCount>>
}

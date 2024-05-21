package woowacourse.movie.data.repository

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.domain.model.Seats

interface ScreenDataSource {
    fun load(): List<ScreenData>

    fun findById(id: Int): Result<ScreenData>

    fun seats(screenId: Int): Seats
}

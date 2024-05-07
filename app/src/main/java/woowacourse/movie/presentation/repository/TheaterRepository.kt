package woowacourse.movie.presentation.repository

import woowacourse.movie.domain.model.Theater
import woowacourse.movie.presentation.uimodel.TheaterUiModel

interface TheaterRepository {
    fun theaters(): List<Theater>

    fun screenTimesCount(
        theaterId: Int,
        movieId: Int,
    ): Int

    fun theatersInfo(movieId: Int): List<TheaterUiModel>

    fun theaterName(theaterId: Int): String
}

package woowacourse.movie.domain.model.tools.cinema

import woowacourse.movie.domain.model.rules.ScreeningTimes
import java.time.LocalTime

data class MovieTimes(
    val times: List<LocalTime>,
) {

    companion object {
        private const val DEFAULT_TIME_STEP = 1

        fun of(startTimes: Int, endTimes: Int, timeStep: Int = DEFAULT_TIME_STEP) =
            MovieTimes(ScreeningTimes.getHours(startTimes, endTimes, timeStep))
    }
}

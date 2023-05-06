package woowacourse.movie.domain.model.tools.cinema

import java.time.LocalTime

data class MovieTimes(
    val times: List<LocalTime>,
) {

    companion object {
        private const val DEFAULT_TIME_STEP = 1

        fun of(startTimes: Int, endTimes: Int, timeStep: Int = DEFAULT_TIME_STEP) =
            MovieTimes(getHours(startTimes, endTimes, timeStep))

        private fun getHours(
            startTime: Int,
            endTime: Int,
            timeStep: Int,
        ): List<LocalTime> {
            return (startTime..endTime step timeStep).map { LocalTime.of(it, 0) }
        }
    }
}

package woowacourse.movie.model.theater

import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.movie.MovieTime
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class ScreeningInfo(
    val screeningTimes: List<MovieTime>,
) : Serializable {
    fun getTimeTable(
        selectedDate: MovieDate,
        currentTime: LocalTime = LocalTime.now(),
    ): List<LocalTime> {
        val today = LocalDate.now()
        return screeningTimes
            .map { it.value }
            .filter { time ->
                if (selectedDate.value == today) {
                    time.isAfter(currentTime)
                } else {
                    true
                }
            }
    }
}

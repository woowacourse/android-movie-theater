package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

// 영화의 상영 시간표
class ScreeningTimeSchedule(
    val date: LocalDate,
    val time: List<LocalTime>,
) : Serializable {
    fun bookableSchedules(
        movie: Movie,
        nowTimeDate: LocalDateTime,
    ): ScreeningTimeSchedule? {
        if (isPast(nowTimeDate) || date !in movie.releaseDate) {
            return null
        }

        if (isToday(nowTimeDate)) {
            val availableTimes = time.filter { it.isAfter(nowTimeDate.toLocalTime()) }
            return ScreeningTimeSchedule(date, availableTimes)
        }
        return this
    }

    private fun isPast(nowTimeDate: LocalDateTime): Boolean = nowTimeDate.toLocalDate().isBefore(date)

    private fun isToday(nowTimeDate: LocalDateTime): Boolean = nowTimeDate.toLocalDate().isEqual(date)
}

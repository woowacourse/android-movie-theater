package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalDateTime

class Schedule(
    val screeningTimeSchedule: ScreeningTimeSchedule,
    val seat: Seats,
) : Serializable {
    fun bookableSchedule(
        movie: Movie,
        nowDateTime: LocalDateTime,
    ): Schedule? {
        screeningTimeSchedule.bookableSchedules(movie, nowDateTime)?.let {
            return Schedule(it, seat)
        }
        return null
    }
}

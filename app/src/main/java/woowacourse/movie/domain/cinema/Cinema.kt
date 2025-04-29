package woowacourse.movie.domain.cinema

import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDate
import java.time.LocalTime

class Cinema(
    screenings: List<Screening>,
    private val showtimePolicy: ShowtimePolicy,
) {
    val screenings = screenings.map { it.copy() }

    fun showtimes(
        screening: Screening,
        date: LocalDate,
    ): List<LocalTime> {
        val showtimes = screening.showtimes(date, showtimePolicy)
        return showtimes
    }
}

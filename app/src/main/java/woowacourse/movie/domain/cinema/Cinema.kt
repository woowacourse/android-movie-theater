package woowacourse.movie.domain.cinema

import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDate
import java.time.LocalTime

data class Cinema(
    val name: String,
    private val _screenings: List<Screening>,
    private val showtimePolicy: ShowtimePolicy,
) {
    val screenings = _screenings.map { it.copy() }

    fun showtimeCount(screening: Screening) = showtimes(screening).size

    fun showtimes(
        screening: Screening,
        date: LocalDate? = null,
    ): List<LocalTime> {
        if (date == null) return screening.showtimes(showtimePolicy)
        return screening.showtimes(date, showtimePolicy)
    }
}

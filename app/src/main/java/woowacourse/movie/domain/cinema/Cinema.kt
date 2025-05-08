package woowacourse.movie.domain.cinema

import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDate
import java.time.LocalTime

class Cinema(
    val name: String,
    screenings: List<Screening>,
    val showtimePolicy: ShowtimePolicy,
) {
    val screenings = screenings.map { it.copy() }

    fun showtimeCount(screening: Screening) = showtimes(screening).size

    fun showtimes(
        screening: Screening,
        date: LocalDate? = null,
    ): List<LocalTime> {
        if (date == null) return screening.showtimes(showtimePolicy)
        return screening.showtimes(date, showtimePolicy)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cinema

        if (name != other.name) return false
        if (showtimePolicy != other.showtimePolicy) return false
        if (screenings != other.screenings) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + showtimePolicy.hashCode()
        result = 31 * result + screenings.hashCode()
        return result
    }
}

package woowacourse.movie.domain.cinema

import woowacourse.movie.domain.reservation.Screening
import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDate
import java.time.LocalTime

class Cinema(
    val name: String,
    private val _screenings: List<Screening>,
    val showtimePolicy: ShowtimePolicy,
) {
    val screening get() = _screenings.map { it.copy() }

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

        return name == other.name &&
            screening == other.screening &&
            showtimePolicy == other.showtimePolicy
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + screening.hashCode()
        result = 31 * result + showtimePolicy.hashCode()
        return result
    }
}

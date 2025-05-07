package woowacourse.movie.domain.cinema

import woowacourse.movie.domain.reservation.ShowtimePolicy
import java.time.LocalDateTime
import java.time.LocalTime

class Cinema(
    val name: String,
    val showtimePolicy: ShowtimePolicy,
) {
    fun showtimeCount(current: LocalDateTime) = showtimes(current).size

    fun showtimes(current: LocalDateTime): List<LocalTime> {
        return showtimePolicy.showtimes(current)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cinema

        return name == other.name &&
            showtimePolicy == other.showtimePolicy
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + showtimePolicy.hashCode()
        return result
    }
}

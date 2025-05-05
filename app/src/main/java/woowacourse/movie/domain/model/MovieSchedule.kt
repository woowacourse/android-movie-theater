package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieSchedule(
    val screeningDateTime: LocalDateTime,
    val seats: Seats = Seats(),
) : Serializable {
    val screeningDate: LocalDate = screeningDateTime.toLocalDate()
    val screeningTime: LocalTime = screeningDateTime.toLocalTime()

    fun isScreeningDate(dateTime: LocalDateTime): Boolean {
        val date = dateTime.toLocalDate()
        return screeningDate.isAfter(date) || screeningDate.isEqual(date)
    }

    fun isTodayScreening(dateTime: LocalDateTime): Boolean {
        return screeningDate.isEqual(dateTime.toLocalDate()) && screeningTime.isAfter(dateTime.toLocalTime())
    }

    fun isFutureScreening(dateTime: LocalDateTime): Boolean {
        return screeningDate.isEqual(dateTime.toLocalDate())
    }

    fun isEqual(dateTime: LocalDateTime): Boolean {
        return this.screeningDateTime.isEqual(dateTime)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieSchedule

        return screeningDateTime == other.screeningDateTime
    }

    override fun hashCode(): Int {
        return screeningDateTime.hashCode()
    }
}

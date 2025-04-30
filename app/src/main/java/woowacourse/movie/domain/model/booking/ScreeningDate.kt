package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate

data class ScreeningDate(
    val screenings: List<LocalDate>,
) : Serializable {
    val startDate: LocalDate = screenings.first()
    val endDate: LocalDate = screenings.last()

    fun bookingDates(today: LocalDate): List<LocalDate> {
        val start = getStartDate(today)
        return screenings.filter { !it.isBefore(start) }.distinct()
    }

    private fun getStartDate(other: LocalDate): LocalDate {
        if (startDate.isAfter(other)) {
            return startDate
        }
        return other
    }
}

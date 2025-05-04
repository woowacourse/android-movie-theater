package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate

data class ScreeningDate(
    val screenings: Set<LocalDate>,
) : Serializable {
    constructor(screenings: List<LocalDate>) : this(screenings.toSet())

    fun bookingDates(today: LocalDate): List<LocalDate> {
        val start = getStartDate(today)
        return screenings.filterNot { it.isBefore(start) }
    }

    private fun getStartDate(other: LocalDate): LocalDate {
        val startDate = screenings.first()
        if (startDate.isAfter(other)) {
            return startDate
        }
        return other
    }
}

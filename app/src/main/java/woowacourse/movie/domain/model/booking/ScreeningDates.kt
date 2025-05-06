package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate

data class ScreeningDates(
    val dates: List<LocalDate>,
) : Serializable {
    val startDate: LocalDate get() = dates.first()
    val endDate: LocalDate get() = dates.last()

    fun bookableDates(today: LocalDate): List<LocalDate> {
        val earliest = getEarliestDate(today)
        return dates.filter { date -> !date.isBefore(earliest) }.distinct()
    }

    private fun getEarliestDate(today: LocalDate): LocalDate {
        return if (startDate.isAfter(today)) {
            startDate
        } else {
            today
        }
    }
}

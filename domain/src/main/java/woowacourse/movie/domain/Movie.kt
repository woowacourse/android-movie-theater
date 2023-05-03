package woowacourse.movie.domain

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Movie(
    val title: String,
    val screeningStartDate: LocalDate,
    val screeningEndDate: LocalDate,
    val runningTime: Minute,
    val posterResourceId: Int,
    val summary: String
) {
    fun getAllScreeningDates(): List<LocalDate> {
        val screeningDates = mutableListOf<LocalDate>()
        var screeningDate = screeningStartDate
        repeat(ChronoUnit.DAYS.between(screeningStartDate, screeningEndDate).toInt() + 1) {
            screeningDates.add(screeningDate)
            screeningDate = screeningDate.plusDays(1)
        }
        return screeningDates
    }
}

package woowacourse.movie.domain.movietime

import java.time.LocalDate

class MovieSchedule(
    private val date: Date,
) {
    fun selectableDates(currentDate: LocalDate): List<LocalDate> {
        val date =
            generateSequence(date.startDate) {
                if (it < date.endDate) it.plusDays(DATE_INTERVAL) else null
            }.filter { it >= currentDate }

        return date.toList()
    }

    companion object {
        private const val DATE_INTERVAL = 1L
    }
}

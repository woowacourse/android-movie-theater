package woowacourse.movie.domain.model

import java.time.LocalDate

data class MovieDate(
    val startDate: LocalDate,
    val endDate: LocalDate,
) {
    var value: LocalDate = LocalDate.now()
        private set

    fun getDateTable(currentDate: LocalDate): List<LocalDate> = dateRangeToTable(currentDate)

    private fun dateRangeToTable(currentDate: LocalDate): List<LocalDate> {
        var minDate: LocalDate = maxOf(startDate, currentDate)
        val dates: MutableList<LocalDate> = mutableListOf<LocalDate>()
        while (minDate <= endDate) {
            dates.add(minDate)
            minDate = minDate.plusDays(1)
        }
        return dates
    }
}

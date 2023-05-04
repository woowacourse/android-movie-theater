package woowacourse.movie.domain.movie

import java.time.LocalDate
import java.time.LocalTime

class ScreeningDateTimes(val dateTimes: Map<LocalDate, List<LocalTime>>) {
    companion object {
        private const val ONE_DAY = 1
        fun of(startDate: LocalDate, endDate: LocalDate, times: List<LocalTime>): ScreeningDateTimes {
            var date = startDate
            val dateTimes = buildMap {
                while (date.isBefore(endDate.plusDays(ONE_DAY.toLong()))) {
                    put(date, times.toList())
                    date = date.plusDays(ONE_DAY.toLong())
                }
            }
            return ScreeningDateTimes(dateTimes)
        }
    }
}

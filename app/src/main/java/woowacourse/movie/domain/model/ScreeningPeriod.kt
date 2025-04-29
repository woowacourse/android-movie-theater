package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class ScreeningPeriod(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val screeningTimes: List<LocalTime>,
    val cinemaId:Int
) : Parcelable {
    fun getAvailableDates(now: LocalDateTime): List<LocalDate> {
        val dates = mutableListOf<LocalDate>()
        var date = now.toLocalDate()
        while (!date.isAfter(endDate)) {
            dates.add(date)
            date = date.plusDays(1)
        }
        return dates.filterNot { it.isBefore(startDate) }
    }

    fun getAvailableTimesFor(
        now: LocalDateTime,
        date: LocalDate,
    ): List<LocalTime> {
        return if (date == now.toLocalDate()) {
            screeningTimes.filter { it.isAfter(now.toLocalTime()) }
        } else {
            screeningTimes
        }
    }
}

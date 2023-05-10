package domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Theater(val name: String, val screenTimes: List<LocalDateTime>) {
    val startDate: LocalDate = screenTimes.min().toLocalDate()
    val endDate: LocalDate = screenTimes.max().toLocalDate()

    fun getAllScreenDates(): List<LocalDate> {
        return screenTimes.map { it.toLocalDate() }
            .toSet()
            .toList()
    }

    fun getScreenTimesOnDate(date: LocalDate): List<LocalTime> {
        return screenTimes.filter { it.toLocalDate() == date }
            .map { it.toLocalTime() }
    }
}

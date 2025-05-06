package woowacourse.movie.domain

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class RunningTimes(
    private val targetDate: LocalDate,
) {
    private fun isWeekdays(): Boolean {
        val dayOfWeek = targetDate.dayOfWeek
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY
    }

    fun runningTimes(): List<LocalTime> {
        val runningTimes = mutableListOf<LocalTime>()
        val currentDateTime = LocalDateTime.now()

        val startTime = if (isWeekdays()) LocalTime.of(9, 0) else LocalTime.of(10, 0)
        val endTime = if (isWeekdays()) LocalTime.of(23, 0) else LocalTime.of(22, 0)
        val startDateTime = LocalDateTime.of(targetDate, startTime)
        val endDateTime = LocalDateTime.of(targetDate, endTime)

        if (currentDateTime.isAfter(endDateTime)) {
            RunningTimesResult.AlreadyFinished
        }

        var movieTime = startDateTime

        while (!movieTime.isAfter(endDateTime)) {
            if (currentDateTime.toLocalDate() == targetDate && movieTime.isBefore(currentDateTime)) {
                movieTime = movieTime.plusHours(2)
                continue
            }

            runningTimes.add(movieTime.toLocalTime())
            movieTime = movieTime.plusHours(2)
        }
        return runningTimes
    }
}

sealed class RunningTimesResult {
    data class Success(val runningTimes : RunningTimes) : RunningTimesResult()
    object AlreadyFinished : RunningTimesResult()
}

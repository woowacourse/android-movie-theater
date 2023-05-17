package com.woowacourse.domain.movie

import com.woowacourse.domain.ScreeningSchedule
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class MovieSchedule(val screeningSchedule: ScreeningSchedule) {

    private val startDate = screeningSchedule.movie.startDate
    private val endDate = screeningSchedule.movie.endDate

    fun getScheduleDates(): List<String> = getDatesBetweenTwoDates().map {
        it.format(dateTimeFormatter)
    }

    private fun getDatesBetweenTwoDates(): List<LocalDate> {
        val numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate)

        return (0..numOfDaysBetween).map {
            startDate.plusDays(it)
        }
    }

    fun getScheduleTimes(): List<String> {
        return screeningSchedule.screeningTime.map { it.toString() }
    }

    companion object {
        private const val DATE_PATTERN = "yyyy-MM-dd"
        private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
    }
}

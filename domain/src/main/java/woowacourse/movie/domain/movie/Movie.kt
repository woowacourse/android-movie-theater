package woowacourse.movie.domain.movie

import java.time.LocalDate
import java.time.LocalTime

data class Movie(
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Minute,
    val summary: String,
    val schedule: Schedule,
) {
    companion object {
        fun of(title: String, startDate: LocalDate, endDate: LocalDate, runningTime: Minute, summary: String, scheduleOfTheater: Map<String, List<LocalTime>>): Movie {
            return Movie(title, startDate, endDate, runningTime, summary, Schedule.of(startDate, endDate, scheduleOfTheater))
        }
    }
}

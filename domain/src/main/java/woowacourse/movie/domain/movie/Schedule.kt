package woowacourse.movie.domain.movie

import java.time.LocalDate
import java.time.LocalTime

data class Schedule(val schedule: Map<String, ScreeningDateTimes>) {
    companion object {
        fun of(startDate: LocalDate, endDate: LocalDate, scheduleOfTheater: Map<String, List<LocalTime>>): Schedule {
            val schedule = mutableMapOf<String, ScreeningDateTimes>()
            scheduleOfTheater.forEach { (name, times) ->
                schedule[name] = ScreeningDateTimes.of(startDate, endDate, times)
            }
            return Schedule(schedule)
        }
    }
}

package woowacourse.movie.domain.model.scheduler

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface Scheduler {
    fun getBookableDates(today: LocalDate = LocalDate.now()): List<LocalDate>

    fun getBookableTimes(
        selectedDate: LocalDate,
        now: LocalDateTime = LocalDateTime.now(),
    ): List<LocalTime>
}

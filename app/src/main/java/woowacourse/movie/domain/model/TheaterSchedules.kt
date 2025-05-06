package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TheaterSchedules(
    private val _schedules: MutableMap<Long, Set<MovieSchedule>>,
) : Serializable {
    private val now get() = LocalDateTime.now()
    val schedules get() = _schedules.toMap()

    fun availableScreeningMovieSchedulesCount(
        movieId: Long,
        dateTime: LocalDateTime = now,
    ): Int {
        return schedules[movieId]?.filter { movieSchedule -> movieSchedule.isScreeningDate(dateTime) }?.size
            ?: 0
    }

    fun screeningDates(
        movieId: Long,
        dateTime: LocalDateTime = LocalDateTime.now(),
    ): List<LocalDate> {
        val movieSchedules = schedules[movieId] ?: return emptyList()
        return movieSchedules.filter { movieSchedule -> movieSchedule.isScreeningDate(dateTime) }
            .map { movieSchedule -> movieSchedule.screeningDate }
            .distinct()
    }

    fun screeningTimes(
        movieId: Long,
        dateTime: LocalDateTime,
    ): List<LocalTime> {
        val movieSchedules = schedules[movieId] ?: return emptyList()

        if (isToday(dateTime)) {
            return movieSchedules.filter { movieSchedule ->
                movieSchedule.isTodayAvailableScreening(now)
            }.map { movieSchedule -> movieSchedule.screeningTime }
        }

        return movieSchedules.filter { movieSchedule ->
            movieSchedule.isFutureAvailableScreeningByDate(dateTime)
        }.map { movieSchedule -> movieSchedule.screeningTime }
    }

    fun movieScheduleByMovieIdAndDateTime(
        movieId: Long,
        dateTime: LocalDateTime,
    ): MovieSchedule? {
        val movieSchedules = schedules[movieId] ?: return null
        return movieSchedules.firstOrNull { movieSchedule -> movieSchedule.isEqual(dateTime) }
    }

    operator fun get(movieId: Long): Set<MovieSchedule> = schedules[movieId] ?: emptySet()

    private fun isToday(dateTime: LocalDateTime): Boolean {
        return now.toLocalDate().isEqual(dateTime.toLocalDate())
    }
}

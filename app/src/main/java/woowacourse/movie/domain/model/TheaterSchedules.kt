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
        val availableSchedules = availableScreeningMovieSchedules(movieId, dateTime)
        return availableSchedules.size
    }

    fun screeningDates(
        movieId: Long,
        dateTime: LocalDateTime = LocalDateTime.now(),
    ): List<LocalDate> {
        val movieSchedules = availableScreeningMovieSchedules(movieId, dateTime)
        return movieSchedules.map { movieSchedule -> movieSchedule.screeningDate }
            .distinct()
            .sorted()
    }

    fun screeningTimes(
        movieId: Long,
        dateTime: LocalDateTime,
    ): List<LocalTime> {
        val movieSchedules = availableScreeningMovieSchedules(movieId, dateTime)

        if (isToday(dateTime)) {
            return movieSchedules.filter { movieSchedule ->
                movieSchedule.isTodayAvailableScreening(now)
            }.map { movieSchedule -> movieSchedule.screeningTime }.sorted()
        }

        return movieSchedules.filter { movieSchedule ->
            movieSchedule.isFutureAvailableScreeningByDate(dateTime)
        }.map { movieSchedule -> movieSchedule.screeningTime }.sorted()
    }

    fun movieScheduleByMovieIdAndDateTime(
        movieId: Long,
        dateTime: LocalDateTime,
    ): MovieSchedule? {
        val movieSchedules = availableScreeningMovieSchedules(movieId, dateTime)
        return movieSchedules.firstOrNull { movieSchedule -> movieSchedule.isEqual(dateTime) }
    }

    operator fun get(movieId: Long): Set<MovieSchedule> = schedules[movieId] ?: emptySet()

    private fun isToday(dateTime: LocalDateTime): Boolean {
        return now.toLocalDate().isEqual(dateTime.toLocalDate())
    }

    private fun availableScreeningMovieSchedules(
        movieId: Long,
        dateTime: LocalDateTime,
    ): List<MovieSchedule> {
        return schedules[movieId]?.filter { movieSchedule -> movieSchedule.isScreeningDate(dateTime) }
            ?: emptyList()
    }
}

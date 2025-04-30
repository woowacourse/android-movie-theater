package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalTime

class Theater(
    val name: String,
    val movieSchedules: List<MovieSchedule>,
) {
    fun movieSchedulesByMovie(
        movie: Movie,
        date: LocalDate,
        time: LocalTime,
    ): Theater {
        val filtered: List<MovieSchedule> =
            movieSchedules.filter { movieSchedule ->
                movieSchedule.isScreeningByDateAndTime(movie, date, time)
            }

        return Theater(name, filtered)
    }
}

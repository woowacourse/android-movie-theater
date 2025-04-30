package woowacourse.movie.domain.model

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class Theater(
    val name: String,
    val movieSchedules: List<MovieSchedule>,
) : Serializable {
    fun theaterByMovie(
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

package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalDateTime

data class Theater(
    val name: String = "DEFAULT_THEATER",
    val allSchedules: Map<Movie, List<Schedule>> = mapOf(),
) : Serializable {
    fun bookableTheater(
        movie: Movie,
        nowLocalDateTime: LocalDateTime,
    ): Theater {
        val schedules: List<Schedule>? = allSchedules[movie]
        val bookableSchedules: List<Schedule> =
            schedules?.mapNotNull { it.bookableSchedule(movie, nowLocalDateTime) } ?: emptyList()
        val filteredSchedules: Map<Movie, List<Schedule>> = mapOf(movie to bookableSchedules)
        return Theater(name, filteredSchedules)
    }

    fun availableTheaterSize(): Int = allSchedules.entries.first().value.size
}

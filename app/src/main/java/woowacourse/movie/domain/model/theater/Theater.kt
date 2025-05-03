package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalDateTime

data class Theater(
    val name: String,
    val allSchedules: Map<Movie, List<Schedule>>,
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
}

package woowacourse.movie.domain.model.cinema

import java.time.LocalDateTime

data class Theater(
    val name: String,
    private val schedules: List<MovieSchedule> = emptyList(),
) {
    fun availableShowTimes(
        movieId: Int,
        now: LocalDateTime,
    ): List<LocalDateTime> =
        schedules
            .filter { it.movieId == movieId }
            .flatMap { it.getTimesAfter(now) }
            .sorted()
}

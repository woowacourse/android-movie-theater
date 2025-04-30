package woowacourse.movie.domain.model.cinema

import java.time.LocalDateTime

class Theaters(
    private val theaters: List<Theater>,
) {
    fun findTheatersByMovieId(
        movieId: Int,
        now: LocalDateTime,
    ): Map<String, List<LocalDateTime>> =
        theaters
            .associate { theater ->
                val available = theater.getAvailableShowTimesFor(movieId, now)
                if (available.isNotEmpty()) theater.name to available else theater.name to emptyList()
            }
}

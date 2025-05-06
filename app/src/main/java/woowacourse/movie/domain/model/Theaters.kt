package woowacourse.movie.domain.model

import java.time.LocalDateTime

class Theaters(
    val value: List<Theater>,
) {
    fun getAllMovies(): List<Movie> =
        value
            .flatMap { theater -> theater.screenings.map { it.movie } }
            .distinct()

    fun getScreeningCountsPerTheater(
        movie: Movie,
        now: LocalDateTime,
    ): List<Pair<String, Int>> =
        value.map { theater ->
            val count = theater.getScreeningCount(movie, now)
            theater.name to count
        }
}

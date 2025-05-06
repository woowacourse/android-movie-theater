package woowacourse.movie.domain.model.theater

import java.time.LocalDateTime

data class Theater(
    val name: String,
    val screenings: List<Screening>,
) {
    fun screeningsCount(movieId: Int) =
        screenings.count { screening ->
            screening.movieId == movieId
        }

    fun screeningTimes(movieId: Int): List<LocalDateTime> =
        screenings.filter { screening ->
            screening.movieId == movieId
        }.map { screening -> screening.time }
}

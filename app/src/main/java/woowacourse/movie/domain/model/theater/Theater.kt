package woowacourse.movie.domain.model.theater

import java.time.LocalDateTime

class Theater(
    val name: String,
    val movieSchedules: List<Screening>,
) {
    fun screeningTimeCount(movieId: Int) =
        movieSchedules
            .asSequence()
            .filter { it.movieId == movieId }
            .mapTo(mutableSetOf()) { it.screenTime.toLocalTime() }
            .size

    fun getMovieScreening(movieId: Int): List<LocalDateTime> =
        movieSchedules
            .filter {
                it.movieId == movieId
            }.map {
                it.screenTime
            }
}

package woowacourse.movie.domain.model.theater

import java.time.LocalDateTime

class Theater(
    val name: String,
    val movieSchedules: List<Screening>,
) {
    fun screeningTimeCount(movieId: Int) = movieSchedules.count { it.movieId == movieId }

    fun getMovieScreening(movieId: Int): List<LocalDateTime> =
        movieSchedules
            .filter {
                it.movieId == movieId
            }.map {
                it.screenTime
            }
}

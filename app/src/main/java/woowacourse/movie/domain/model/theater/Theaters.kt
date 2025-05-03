package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalDateTime

class Theaters(
    val theaters: List<Theater>,
) : Serializable {
    fun availableTheatersSchedules(
        movie: Movie,
        nowLocalDateTime: LocalDateTime = LocalDateTime.now(),
    ): List<Pair<Movie, List<Schedule>>> = theaters.map { it.schedulesOf(movie, nowLocalDateTime) }
}

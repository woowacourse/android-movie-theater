package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Movie
import java.io.Serializable
import java.time.LocalDateTime

class Theaters(
    val theaters: List<Theater>,
) : Serializable {
    fun bookableTheaters(
        movie: Movie,
        nowLocalDateTime: LocalDateTime = LocalDateTime.now(),
    ): Theaters = Theaters(theaters.map { it.bookableTheater(movie, nowLocalDateTime) })
}

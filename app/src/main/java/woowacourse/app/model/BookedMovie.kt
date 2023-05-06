package woowacourse.app.model

import woowacourse.domain.movie.Movie
import java.time.LocalDateTime

data class BookedMovie(
    val movie: Movie,
    val theaterId: Long,
    val ticketCount: Int,
    val bookedDateTime: LocalDateTime,
)

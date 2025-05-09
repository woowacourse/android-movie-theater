package woowacourse.movie.domain.model.movie

import woowacourse.movie.domain.model.seat.Seat
import java.io.Serializable
import java.time.LocalDateTime

data class MovieTicket(
    val movieTitle: String,
    val theaterName: String,
    val screeningDateTime: LocalDateTime,
    val headCount: Int,
    val amount: Int = DEFAULT_AMOUNT,
    val seats: List<Seat> = emptyList(),
) : Serializable {
    companion object {
        private const val DEFAULT_AMOUNT = 0
    }
}

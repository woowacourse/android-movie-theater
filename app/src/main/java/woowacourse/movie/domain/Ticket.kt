package woowacourse.movie.domain

import woowacourse.movie.domain.movieseat.Seats
import java.io.Serializable
import java.time.LocalDateTime

data class Ticket(
    val title: String,
    val date: LocalDateTime,
    val personnel: Int,
    val theaterName: String,
    val seats: Seats,
) : Serializable {
    companion object {
        const val CANCEL_DEADLINE = 15
    }
}

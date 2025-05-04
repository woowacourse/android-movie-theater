package woowacourse.movie.domain.model.cinema.ticket

import woowacourse.movie.domain.model.cinema.screen.Seat
import java.time.LocalDateTime

class Ticket(
    val title: String,
    val theaterName: String,
    val reservationDateTime: LocalDateTime,
    val seats: List<Seat>,
    val price: Int,
) {
    val count = seats.count()
}

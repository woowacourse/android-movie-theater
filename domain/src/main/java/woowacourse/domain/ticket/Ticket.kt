package woowacourse.domain.ticket

import woowacourse.domain.movie.Movie
import woowacourse.domain.policy.TicketPriceAdapter
import java.time.LocalDateTime

data class Ticket(
    val movie: Movie,
    val bookedDateTime: LocalDateTime,
    val seat: Seat,
) {
    val price get() = TicketPriceAdapter.getPayment(this)
}

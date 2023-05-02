package woowacourse.domain.ticket

import woowacourse.domain.policy.TicketPriceAdapter
import java.time.LocalDateTime

data class Ticket(
    val movieId: Long,
    val movieTitle: String,
    val bookedDateTime: LocalDateTime,
    val seat: Seat,
) {
    val price get() = TicketPriceAdapter().getPayment(this)
}

package woowacourse.domain.reservation

import woowacourse.domain.PaymentType
import woowacourse.domain.movie.Movie
import woowacourse.domain.ticket.Ticket
import java.time.LocalDateTime

data class Reservation(
    val tickets: Set<Ticket>,
    val id: Long,
    val paymentType: PaymentType = PaymentType.OFFLINE,
) {
    val payment: Int get() = tickets.sumOf { it.price }
    val movie: Movie get() = tickets.first().movie
    val bookedDateTime: LocalDateTime get() = tickets.first().bookedDateTime
    val count: Int get() = tickets.size
    val alarmCycle: Long = 30
}

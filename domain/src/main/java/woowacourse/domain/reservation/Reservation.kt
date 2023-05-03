package woowacourse.domain.reservation

import woowacourse.data.bookingHistory.BookingDatabase
import woowacourse.domain.PaymentType
import woowacourse.domain.ticket.Ticket
import java.time.LocalDateTime

data class Reservation(
    val tickets: Set<Ticket>,
    val id: Long = BookingDatabase.getNewId(),
    val paymentType: PaymentType = PaymentType.OFFLINE,
) {
    val payment: Int get() = tickets.sumOf { it.price }
    val movieId: Long get() = tickets.first().movieId
    val bookedDateTime: LocalDateTime get() = tickets.first().bookedDateTime
    val count: Int get() = tickets.size
    val alarmCycle: Long = 30
}

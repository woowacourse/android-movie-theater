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
    val payment: Int = tickets.sumOf { it.price }
    val movieId: Long = tickets.first().movieId
    val bookedDateTime: LocalDateTime = tickets.first().bookedDateTime
    val count: Int = tickets.size
    val alarmCycle: Long = 30
}

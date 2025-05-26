package woowacourse.movie.domain.model.cinema.ticket

import woowacourse.movie.domain.model.cinema.screen.Seat
import java.time.LocalDateTime

class TicketBundle(
    val title: String,
    val dateTime: LocalDateTime,
    val theater: String,
    val tickets: List<Ticket>,
) {
    val size: Int = tickets.size
    val totalPrice: Int = tickets.sumOf { it.price }
    val labels: List<Seat> = tickets.map { it.seat }
    fun getAlarmTime(): LocalDateTime = dateTime.minusMinutes(30)
    companion object {
        fun bundleOf(
            title: String,
            dateTime: LocalDateTime,
            theater: String,
            tickets: List<Ticket>,
        ): TicketBundle {
            require(tickets.isNotEmpty()) { ERROR_TICKET_BUNDLE_EMPTY }

            return TicketBundle(title, dateTime, theater, tickets)
        }

        const val DEFAULT_TOTAL_PRICE = 0
        private const val ERROR_TICKET_BUNDLE_EMPTY = "티켓 묶음은 하나 이상의 티켓이 있어야 합니다"
    }
}

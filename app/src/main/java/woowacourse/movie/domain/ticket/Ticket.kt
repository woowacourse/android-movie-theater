package woowacourse.movie.domain.ticket

import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
import java.io.Serializable
import java.time.LocalDateTime

data class Ticket(
    val id: Long? = null,
    val title: String,
    val count: Int,
    val showtime: LocalDateTime,
    val cinemaName: String,
    val seats: Set<Seat>,
    val purchaseType: PurchaseType,
) : Serializable {
    val price: Int = count * TICKET_PRICE

    fun notifyBeforeMinutes(): LocalDateTime =
        when (purchaseType) {
            PurchaseType.DEFAULT -> showtime.minusMinutes(DEFAULT_NOTIFY_BEFORE_MINUTES)
        }

    fun addSeat(seat: Seat) = this.copy(seats = seats + seat)

    fun removeSeat(seat: Seat) = this.copy(seats = seats - seat)

    fun updateSeats(seats: Set<Seat>) = this.copy(seats = seats)

    companion object {
        const val TICKET_PRICE = 13_000
        private const val DEFAULT_NOTIFY_BEFORE_MINUTES = 30L
    }
}

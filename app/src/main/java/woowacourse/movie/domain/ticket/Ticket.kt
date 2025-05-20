package woowacourse.movie.domain.ticket

import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
import java.io.Serializable
import java.time.LocalDateTime

data class Ticket(
    val title: String,
    val count: Int,
    val showtime: LocalDateTime,
    val cinemaName: String,
    val seats: Set<Seat>,
    val purchaseType: PurchaseType,
) : Serializable {
    fun addSeat(seat: Seat) = this.copy(seats = seats + seat)

    fun removeSeat(seat: Seat) = this.copy(seats = seats - seat)

    fun updateSeats(seats: Set<Seat>) = this.copy(seats = seats)
}

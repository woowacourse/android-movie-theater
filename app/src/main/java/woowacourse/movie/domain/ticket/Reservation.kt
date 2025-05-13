package woowacourse.movie.domain.ticket

import woowacourse.movie.domain.reservation.Seat
import java.io.Serializable
import java.time.LocalDateTime

data class Reservation(
    val title: String,
    val showtime: LocalDateTime,
    val seats: Set<Seat>,
    val cinemaName: String,
) : Serializable {
    val price: Int = seats.sumOf(Seat::price)
}

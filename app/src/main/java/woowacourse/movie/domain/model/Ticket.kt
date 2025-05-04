package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.seat.Seats
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

data class Ticket(
    val movie: Movie,
    val theater: String,
    val showtime: LocalDateTime = LocalDateTime.of(movie.startDate, LocalTime.MIDNIGHT),
    val headCount: HeadCount = HeadCount(),
    val seats: Seats = Seats(),
) : Serializable {
    fun totalPrice(): Int = seats.totalPrice()

    fun isFull(): Boolean = seats.size() == headCount.value
}

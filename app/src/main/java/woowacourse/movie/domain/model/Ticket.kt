package woowacourse.movie.domain.model

import woowacourse.movie.domain.model.seat.Seat
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class Ticket(
    val id: Long,
    val title: String,
    val bookingDate: LocalDate,
    val bookingTime: LocalTime,
    val theaterName: String,
    val count: PeopleCount,
    val seats: Set<Seat>,
    val price: Int,
) : Serializable {
    companion object {
        fun initialize(
            booking: Booking,
            seats: Set<Seat>,
            price: Int,
        ): Ticket {
            return Ticket(
                id = 0L,
                title = booking.movieTitle,
                bookingDate = booking.bookingDate,
                bookingTime = booking.bookingTime,
                theaterName = booking.theaterName,
                count = booking.count,
                seats = seats,
                price = price,
            )
        }
    }
}

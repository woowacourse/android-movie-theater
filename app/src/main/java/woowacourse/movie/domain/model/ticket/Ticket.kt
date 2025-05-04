package woowacourse.movie.domain.model.ticket

import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.seat.Seat
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

data class Ticket(
    val movieTitle: String,
    val theaterName: String,
    val screeningDate: LocalDate,
    val screeningTime: LocalTime,
    val count: AdmissionCount,
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
                movieTitle = booking.movieTitle,
                theaterName = booking.theaterName,
                screeningDate = booking.screeningDate,
                screeningTime = booking.screeningTime,
                count = booking.count,
                seats = seats,
                price = price,
            )
        }
    }
}

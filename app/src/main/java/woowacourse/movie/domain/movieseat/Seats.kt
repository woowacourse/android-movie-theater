package woowacourse.movie.domain.movieseat

import java.io.Serializable

class Seats(private val seats: Set<Seat> = emptySet()) : Serializable {
    val selectedSeats get() = seats.toList()

    fun addSeat(seat: Seat): Seats = Seats(seats + seat)

    fun removeSeat(seat: Seat): Seats = Seats(seats - seat)

    fun selectedLimit(limit: Int): Boolean = seats.size >= limit

    fun canSelect(limit: Int): Boolean = seats.size == limit

    fun reservationPrice() = seats.sumOf { it.seatPrice() }
}

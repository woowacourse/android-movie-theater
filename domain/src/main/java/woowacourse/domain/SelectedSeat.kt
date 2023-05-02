package woowacourse.domain

import woowacourse.domain.movie.Movie
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository
import woowacourse.domain.ticket.Seat
import java.time.LocalDateTime

class SelectedSeat(private val ticketCount: Int) {
    private val _seats = mutableSetOf<Seat>()
    val seats get() = _seats.toSet()

    val isSeatFull: Boolean
        get() = _seats.size == ticketCount

    fun clickSeat(seat: Seat): SelectResult {
        if (_seats.contains(seat)) {
            return unSelectSeat(seat)
        }
        return selectSeat(seat)
    }

    private fun selectSeat(seat: Seat): SelectResult {
        if (isSeatFull) {
            return SelectResult.Select.Full
        }
        _seats.add(seat)
        return SelectResult.Select.Success
    }

    private fun unSelectSeat(seat: Seat): SelectResult {
        _seats.remove(seat)
        return SelectResult.Unselect
    }

    fun reserve(movie: Movie, dateTime: LocalDateTime): Reservation {
        val tickets = seats.map { movie.reserve(dateTime, it) }
        val reservation = Reservation(tickets.toSet())
        ReservationRepository.addReservation(reservation)
        return reservation
    }

    fun getPayment(movie: Movie, dateTime: LocalDateTime): Int {
        val tickets = seats.map { movie.reserve(dateTime, it) }
        return tickets.sumOf { it.price }
    }
}

package woowacourse.domain

import woowacourse.domain.movie.Movie
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.reservation.ReservationRepository
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.Ticket
import java.time.LocalDateTime

class BoxOffice(private val reservationRepository: ReservationRepository) {

    fun createTickets(
        movie: Movie,
        bookedDateTime: LocalDateTime,
        seats: Set<Seat>,
    ): List<Ticket> {
        if (bookedDateTime.toLocalDate() !in movie.screeningDates) {
            throw IllegalArgumentException("상영 기간이 아닙니다.")
        }
        return seats.map { Ticket(movie, bookedDateTime, it) }
    }

    fun getPayment(movie: Movie, bookedDateTime: LocalDateTime, seats: Set<Seat>): Int {
        val tickets = createTickets(movie, bookedDateTime, seats)
        return tickets.sumOf { it.price }
    }

    fun makeReservation(tickets: Set<Ticket>): Reservation {
        return reservationRepository.makeReservation(tickets)
    }

    fun makeReservation(
        movie: Movie,
        bookedDateTime: LocalDateTime,
        seats: Set<Seat>,
    ): Reservation {
        val tickets = createTickets(movie, bookedDateTime, seats)
        return makeReservation(tickets.toSet())
    }
}

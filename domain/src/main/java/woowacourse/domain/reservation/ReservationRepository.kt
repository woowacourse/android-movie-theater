package woowacourse.domain.reservation

import woowacourse.data.bookingHistory.BookingDatabase
import woowacourse.data.bookingHistory.BookingEntity
import woowacourse.data.bookingHistory.SeatEntity
import woowacourse.domain.movie.MovieRepository.getMovie
import woowacourse.domain.movie.MovieRepository.toMovie
import woowacourse.domain.movie.MovieRepository.toMovieEntity
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.SeatRank
import woowacourse.domain.ticket.Ticket

object ReservationRepository {
    fun getReservations(): List<Reservation> {
        return BookingDatabase.bookings.mapNotNull { it.toReservation() }
    }

    fun getReservation(id: Long): Reservation? {
        return BookingDatabase.selectBooking(id)?.toReservation()
    }

    fun addReservation(reservation: Reservation) {
        BookingDatabase.insertBooking(reservation.toBookingEntity())
    }

    private fun Reservation.toBookingEntity(): BookingEntity {
        return BookingEntity(
            id = BookingDatabase.getNewId(),
            movie = getMovie(this.movieId).toMovieEntity(),
            bookedDateTime = this.bookedDateTime,
            paymentType = this.paymentType.ordinal,
            seats = this.tickets.map { it.seat.toSeatEntity() },
        )
    }

    private fun BookingEntity.toReservation(): Reservation? {
        return Reservation(
            id = this.id,
            tickets = this.seats.map {
                Ticket(this.movie.toMovie(), this.bookedDateTime, it.toSeat())
            }.toSet(),
            paymentType = woowacourse.domain.PaymentType.find(this.paymentType) ?: return null,
        )
    }

    private fun SeatEntity.toSeat(): Seat {
        return Seat(
            SeatRank.find(this.rank),
            Position(this.row, this.column),
        )
    }

    private fun Seat.toSeatEntity(): SeatEntity {
        return SeatEntity(
            rank = this.rank.ordinal,
            row = this.position.row,
            column = this.position.column,
        )
    }
}

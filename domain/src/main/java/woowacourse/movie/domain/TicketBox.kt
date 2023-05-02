package woowacourse.movie.domain

import woowacourse.movie.domain.dataSource.ReservationDataSource
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.seat.Seats

class TicketBox(dataSource: ReservationDataSource) {

    private val reservationRepository = ReservationRepository(dataSource)

    fun ticketing(
        movie: Movie,
        reservationDetail: ReservationDetail,
        selectedSeats: Seats
    ): Reservation {
        val price = selectedSeats.calculateDiscountedPrice(reservationDetail)
        val reservation = Reservation(movie, reservationDetail, selectedSeats, price)
        reservationRepository.addData(reservation)
        return reservation
    }
}

package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Seat

interface SeatRepository {
    fun addSeats(seats: List<Seat>, reservationId: Int)
    fun findSeatsByReservationId(id: Int): List<Seat>
}

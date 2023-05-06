package woowacourse.movie.sql

import domain.Reservation

interface ReservationDbHelperInterface {
    fun saveReservation(reservation: Reservation)
    fun getReservations(): List<Reservation>
}

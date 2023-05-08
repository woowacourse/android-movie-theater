package woowacourse.movie.db

import domain.Reservation

interface DBHelper {
    fun saveReservation(reservation: Reservation)
    fun getReservations(): List<Reservation>
}

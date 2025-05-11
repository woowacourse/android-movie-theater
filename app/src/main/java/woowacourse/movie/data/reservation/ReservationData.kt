package woowacourse.movie.data.reservation

import woowacourse.movie.ReservationDao
import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.toEntity

interface ReservationData {
    fun addReservation(reservation: Reservation)

    fun reservations(): List<Reservation>
}

class LocalReservationData(
    private val reservationDao: ReservationDao,
) : ReservationData {
    override fun addReservation(reservation: Reservation) {
        reservationDao.addReservation(reservation.toEntity())
    }

    override fun reservations(): List<Reservation> =
        reservationDao
            .reservations()
            .map { it.toDomain() }
            .sortedByDescending { it.showtime }
}

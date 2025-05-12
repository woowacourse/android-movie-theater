package woowacourse.movie.data.reservation

import woowacourse.movie.domain.ticket.Reservation
import woowacourse.movie.view.reservation.ReservationDao
import woowacourse.movie.view.reservation.toEntity

interface ReservationData {
    fun add(reservation: Reservation)

    fun reservations(): List<Reservation>
}

class LocalReservationData(
    private val reservationDao: ReservationDao,
) : ReservationData {
    override fun add(reservation: Reservation) {
        reservationDao.addReservation(reservation.toEntity())
    }

    override fun reservations(): List<Reservation> =
        reservationDao
            .reservations()
            .map { it.toDomain() }
            .sortedBy { it.showtime }
}

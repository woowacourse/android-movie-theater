package woowacourse.movie.data

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.repository.ReservationRepository

object ReservationMockRepository : ReservationRepository {

    private val reservations = mutableListOf<Reservation>()
    override fun add(reservation: Reservation): Int {
        reservations.add(reservation)
        return reservation.id
    }

    override fun findAll(): List<Reservation> {
        return reservations.toList()
    }
}

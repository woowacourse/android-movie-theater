package woowacourse.movie.data.reservation

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.reservation.Reservation

object ReservationMockRepository : ReservationRepository {

    private val reservations = mutableListOf<Reservation>()
    override fun add(reservation: Reservation) {
        reservations.add(reservation)
    }

    override fun findAll(): List<Reservation> {
        return reservations.toList()
    }
}

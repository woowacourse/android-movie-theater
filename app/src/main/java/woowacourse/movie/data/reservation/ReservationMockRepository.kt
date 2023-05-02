package woowacourse.movie.data.reservation

import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.repository.ReservationRepository

object ReservationMockRepository : ReservationRepository {

    private val reservations = mutableListOf<Reservation>()
    override fun add(reservation: Reservation) {
        reservations.add(reservation)
    }

    override fun findAll(): List<Reservation> {
        return reservations.toList()
    }
}

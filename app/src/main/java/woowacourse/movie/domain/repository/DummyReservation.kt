package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Reservation

object DummyReservation : ReservationRepository {
    private val reservations = mutableListOf<Reservation>()

    override fun saveReservation(reservation: Reservation): Result<Long> {
        return runCatching {
            val id = reservations.size + 1.toLong()
            reservations.add(reservation.copy(id = id))
            id
        }
    }

    override fun findByReservationId(id: Long): Result<Reservation> {
        return runCatching {
            val reservation = reservations.find { reservation -> reservation.id == id }
            reservation ?: throw NoSuchElementException("예약 정보를 찾을 수 없습니다.")
        }
    }
}

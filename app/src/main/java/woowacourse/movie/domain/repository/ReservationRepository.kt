package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.Reservation

interface ReservationRepository {
    fun saveReservation(reservation: Reservation): Result<Long>

    fun findByReservationId(id: Long): Result<Reservation>
}

package woowacourse.movie.domain.repository

import woowacourse.movie.domain.reservation.Reservation

interface ReservationRepository {

    fun add(reservation: Reservation)

    fun findAll(): List<Reservation>
}

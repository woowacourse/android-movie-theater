package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Reservation

interface ReservationRepository {

    fun add(reservation: Reservation)

    fun findAll(): List<Reservation>
}

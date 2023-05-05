package woowacourse.movie.domain.repository

import woowacourse.movie.domain.Reservation

interface ReservationRepository {

    fun add(reservation: Reservation): Int

    fun findAll(): List<Reservation>
}

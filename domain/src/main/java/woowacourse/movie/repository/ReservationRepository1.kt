package woowacourse.movie.repository

import woowacourse.movie.domain.screening.Reservation

interface ReservationRepository1 {

    fun save(reservation: Reservation): Long

    fun findById(id: Long): Reservation

    fun findAll(): List<Reservation>
}
package woowacourse.movie.domain

import woowacourse.movie.domain.model.ticketing.Ticket

interface ReservationRepository {
    fun getAll(): List<Ticket>

    fun insert(vararg ticket: Ticket)
}

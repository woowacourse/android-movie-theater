package woowacourse.movie.domain

import woowacourse.movie.domain.model.ticketing.Ticket

interface ReservationDaoListener {
    fun getAll(): List<Ticket>

    fun insert(vararg ticket: Ticket)
}

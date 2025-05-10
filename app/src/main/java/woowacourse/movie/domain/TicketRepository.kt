package woowacourse.movie.domain

import woowacourse.movie.view.reservation.Ticket

interface TicketRepository {
    fun getAll(): List<Ticket>

    fun insertAll(vararg ticket: Ticket)
}

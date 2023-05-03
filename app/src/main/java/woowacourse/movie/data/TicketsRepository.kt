package woowacourse.movie.data

import woowacourse.movie.model.TicketsState

interface TicketsRepository {
    fun allTickets(): List<TicketsState>

    fun addTicket(ticket: TicketsState): Boolean
}

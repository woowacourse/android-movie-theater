package woowacourse.movie.repository

import woowacourse.movie.model.TicketsState

object TicketsRepository {
    private val tickets: MutableList<TicketsState> = mutableListOf()

    fun allTickets(): List<TicketsState> = tickets.toList()

    fun addTicket(ticket: TicketsState) = this.tickets.add(ticket)
}

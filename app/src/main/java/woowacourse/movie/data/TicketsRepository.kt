package woowacourse.movie.data

import woowacourse.movie.model.TicketsState

object TicketsRepository {

    private val tickets: MutableList<TicketsState> = mutableListOf()

    fun getAllTickets(): List<TicketsState> = tickets.toList()

    fun addTicket(ticket: TicketsState) = this.tickets.add(ticket)
}

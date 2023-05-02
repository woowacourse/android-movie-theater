package woowacourse.movie.data

import woowacourse.movie.model.TicketsState

object TicketsRepository {

    fun allTickets(): List<TicketsState> = tickets.toList()

    fun addTicket(ticket: TicketsState) = this.tickets.add(ticket)

    private val tickets: MutableList<TicketsState> = mutableListOf()
}

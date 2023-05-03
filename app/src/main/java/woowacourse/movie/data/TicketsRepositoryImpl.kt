package woowacourse.movie.data

import woowacourse.movie.model.TicketsState

object TicketsRepositoryImpl : TicketsRepository {
    override fun allTickets(): List<TicketsState> = tickets.toList()

    override fun addTicket(ticket: TicketsState): Boolean = this.tickets.add(ticket)

    private val tickets: MutableList<TicketsState> = mutableListOf()
}

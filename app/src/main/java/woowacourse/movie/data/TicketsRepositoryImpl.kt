package woowacourse.movie.data

import com.example.domain.model.Tickets
import com.example.domain.repository.TicketsRepository

object TicketsRepositoryImpl : TicketsRepository {
    private val tickets: MutableList<Tickets> = mutableListOf()

    override fun allTickets(): List<Tickets> = tickets.toList()

    override fun addTicket(ticket: Tickets) {
        this.tickets.add(ticket)
    }
}

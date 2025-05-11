package woowacourse.movie.domain

interface TicketRepository {
    fun getAll(): List<Ticket>

    fun insertAll(vararg ticket: Ticket)
}

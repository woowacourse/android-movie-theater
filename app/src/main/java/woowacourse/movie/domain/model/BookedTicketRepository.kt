package woowacourse.movie.domain.model

interface BookedTicketRepository {
    fun insert(bookedTicket: BookedTicket, onIdReceived:(Long) -> Unit)

    fun fetchById(id: Long, onTicketLoaded: (BookedTicket) -> Unit)

    fun fetchAll(onTicketsLoaded: (List<BookedTicket>) -> Unit)
}

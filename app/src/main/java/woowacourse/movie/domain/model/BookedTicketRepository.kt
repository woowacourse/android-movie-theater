package woowacourse.movie.domain.model

interface BookedTicketRepository {
    fun insert(bookedTicket: BookedTicket): Long

    fun fetchById(id: Long, onTicketLoaded: (BookedTicket) -> Unit)

    fun fetchAll(onTicketsLoaded: (List<BookedTicket>) -> Unit)
}

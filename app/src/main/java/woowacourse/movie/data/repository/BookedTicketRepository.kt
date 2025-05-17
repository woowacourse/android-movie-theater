package woowacourse.movie.data.repository

import woowacourse.movie.data.entity.BookedTicketEntity

interface BookedTicketRepository {
    fun getAll(): List<BookedTicketEntity>

    fun insertBookedTicket(bookedTicketEntity: BookedTicketEntity)
}

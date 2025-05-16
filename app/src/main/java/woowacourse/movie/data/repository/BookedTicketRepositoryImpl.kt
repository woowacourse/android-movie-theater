package woowacourse.movie.data.repository

import woowacourse.movie.data.dao.BookedTicketDao
import woowacourse.movie.data.entity.BookedTicketEntity

class BookedTicketRepositoryImpl(
    val bookedTicketDao: BookedTicketDao,
) : BookedTicketRepository {
    override fun getAll(): List<BookedTicketEntity> = bookedTicketDao.getAll()

    override fun insertBookedTicket(bookedTicketEntity: BookedTicketEntity) {
        bookedTicketDao.insertBookedTicket(bookedTicketEntity)
    }
}

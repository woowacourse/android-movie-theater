package woowacourse.movie.presentation.view.history

import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.toTicket
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import java.time.LocalDateTime

class ReservationHistoryRepository(
    private val dao: ReservationDao,
) {
    fun getReservation(): List<TicketBundle> {
        val bundleEntityList = dao.findAllTicketBundles()
        return bundleEntityList.map {
            TicketBundle(
                it.bundle.title,
                LocalDateTime.parse(it.bundle.dateTime),
                it.bundle.theater,
                it.tickets.map { ticket -> ticket.toTicket() },
            )
        }
    }
}

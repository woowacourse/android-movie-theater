package woowacourse.movie.presentation.view.home.reservation.seat

import woowacourse.movie.data.dao.ReservationDao
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.data.extension.toEntity
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import woowacourse.movie.domain.model.cinema.ticket.toLabel

class ReservationRepository(
    private val dao: ReservationDao,
) {
    fun saveReservation(ticketBundle: TicketBundle) {
        val bundleEntity = ticketBundle.toEntity()
        val bundleId = dao.saveTicketBundle(bundleEntity).toInt()
        val ticketEntities =
            ticketBundle.tickets.map {
                TicketEntity(
                    seatLabel = it.seat.toLabel(),
                    price = it.price,
                    bundleId = bundleId,
                    type = it.seat.type.toString(),
                )
            }
        dao.saveTickets(ticketEntities)
    }
}

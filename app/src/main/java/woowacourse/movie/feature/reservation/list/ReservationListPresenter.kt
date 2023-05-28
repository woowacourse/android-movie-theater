package woowacourse.movie.feature.reservation.list

import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val ticketsRepository: TicketsRepository
) : ReservationListContract.Presenter {

    override fun loadListItems() {
        view.setTicketList(ticketsRepository.getAllTickets())
    }

    override fun showTicketsConfirm(tickets: TicketsState) {
        view.showTicketsConfirm(tickets)
    }
}

package woowacourse.movie.feature.reservation.list

import woowacourse.movie.data.TicketsRepositoryImpl
import woowacourse.movie.model.TicketsState
import woowacourse.movie.model.mapper.asPresentation

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val ticketsRepository: TicketsRepositoryImpl
) : ReservationListContract.Presenter {

    override fun loadListItems() {
        view.setTicketList(ticketsRepository.getAllTickets().map { it.asPresentation() })
    }

    override fun showTicketsConfirm(tickets: TicketsState) {
        view.showTicketsConfirm(tickets)
    }
}

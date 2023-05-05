package woowacourse.movie.feature.reservationList

import woowacourse.movie.data.TicketsRepository

class ReservationPresenter(
    val view: ReservationListContract.View,
    private val ticketsRepository: TicketsRepository
) : ReservationListContract.Presenter {
    override fun loadTicketsItemList() {
        val tickets = ticketsRepository.allTickets().map {
            it.convertToItemModel { tickets -> view.navigateReservationConfirm(tickets) }
        }
        view.updateItems(tickets)
    }
}

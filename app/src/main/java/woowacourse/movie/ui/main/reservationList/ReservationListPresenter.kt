package woowacourse.movie.ui.main.reservationList

import woowacourse.movie.data.TicketsRepository

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val ticketsRepository: TicketsRepository = TicketsRepository
) : ReservationListContract.Presenter {
    override fun getReservationList() {
        view.setAdapter(ticketsRepository.allTickets())
    }
}

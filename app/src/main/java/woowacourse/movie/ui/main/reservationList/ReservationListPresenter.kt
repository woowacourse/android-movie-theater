package woowacourse.movie.ui.main.reservationList

import woowacourse.movie.dbHelper.TicketsDbHelper

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val ticketsDbHelper: TicketsDbHelper
) : ReservationListContract.Presenter {
    override fun setUpReservationList() {
        view.setTickets(ticketsDbHelper.getAll())
    }
}

package woowacourse.movie.presenter.reservationDetails

import woowacourse.movie.model.reservation.ReservationDao

class ReservationDetailPresenter(
    private val view: ReservationDetailContracts.View,
    private val dao: ReservationDao,
) : ReservationDetailContracts.Presenter {
    override fun loadReservations() {
        Thread {
            val reservations = dao.findReservations()
            view.showReservations(reservations)
        }.start()
    }

    override fun requestReservationComplete(ticketId: Long) {
        Thread {
            val movieTicket =
                dao.findReservation(ticketId) ?: return@Thread
            view.showReservationCompleteView(movieTicket)
        }.start()
    }
}

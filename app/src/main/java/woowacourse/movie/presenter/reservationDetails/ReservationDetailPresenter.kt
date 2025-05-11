package woowacourse.movie.presenter.reservationDetails

class ReservationDetailPresenter(
    private val view: ReservationDetailContracts.View,
    private val dao: ReservationDao,
    private val runAsync: (Runnable) -> Unit = { runnable -> Thread(runnable).start() },
) : ReservationDetailContracts.Presenter {
    override fun loadReservations() {
        runAsync.invoke {
            val reservations = dao.findReservations()
            view.showReservations(reservations)
        }
    }

    override fun requestReservationComplete(ticketId: Long) {
        runAsync.invoke {
            val movieTicket =
                dao.findReservation(ticketId) ?: return@invoke
            view.showReservationCompleteView(movieTicket)
        }
    }
}

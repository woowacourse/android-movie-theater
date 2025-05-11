package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.model.ticket.MovieTicket

class ReservationCompletePresenter(
    private val view: ReservationCompleteContracts.View,
) : ReservationCompleteContracts.Presenter {
    override fun updateTicketData(movieTicket: MovieTicket) {
        view.showMovieTicket(movieTicket)
    }

    override fun requestErrorDialogMessage() {
        view.showErrorDialogMessage()
    }

    override fun requestAlarmPermissionScreen() {
        view.showAlarmPermissionScreen()
    }
}

package woowacourse.movie.presenter.reservationComplete

import android.content.SharedPreferences
import woowacourse.movie.model.ticket.MovieTicket

class ReservationCompletePresenter(
    private val view: ReservationCompleteContracts.View,
    private val prefs: SharedPreferences,
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

    override fun requestAlarm(movieTicket: MovieTicket) {
        if (!prefs.getBoolean(movieTicket.ticketId.toString(), false)) {
            with(prefs.edit()) {
                putBoolean(movieTicket.ticketId.toString(), true)
                apply()
            }
            view.showAlarmBeforeMovieStart(movieTicket)
        }
    }
}

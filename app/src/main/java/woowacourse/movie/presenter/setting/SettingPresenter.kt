package woowacourse.movie.presenter.setting

import android.content.Context
import woowacourse.movie.notification.TicketNotification
import woowacourse.movie.repository.ReservationTicketRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val repository: ReservationTicketRepository,
) : SettingContract.Presenter {
    override fun loadSavedSetting(isPushSetting: Boolean) {
        view.showSavedSetting(isPushSetting)
    }

    override fun settingAlarm(
        context: Context,
        isPushSetting: Boolean,
    ) {
        view.saveSetting(isPushSetting)
        Thread {
            val tickets = repository.loadReservationTickets()
            if (isPushSetting) {
                tickets.forEach { reservationTicket ->
                    TicketNotification.setNotification(
                        context = context,
                        ticketId = reservationTicket.ticketId,
                        movieTitle = reservationTicket.movieTitle,
                        screeningDateTime = reservationTicket.screeningDateTime,
                    )
                }
            } else {
                TicketNotification.cancelNotification(context)
            }
        }.start()
    }
}

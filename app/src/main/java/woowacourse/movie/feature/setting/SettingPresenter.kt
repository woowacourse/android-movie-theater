package woowacourse.movie.feature.setting

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.TicketRepository

class SettingPresenter(
    private val view: SettingContract.View
) : SettingContract.Presenter {
    override fun setTicketsAlarm(ticketRepository: TicketRepository) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val tickets = ticketRepository.findAll()
            handler.post {
                tickets.forEach { view.setTicketAlarm(it) }
            }
        }.start()
    }

    override fun cancelTicketsAlarm(ticketRepository: TicketRepository) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val tickets = ticketRepository.findAll()
            handler.post {
                tickets.forEach { view.cancelTicketAlarm(it) }
            }
        }.start()
    }
}

package woowacourse.movie.feature.setting

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import kotlin.concurrent.thread

class SettingPresenter(private val view: SettingContract.View) : SettingContract.Presenter {
    override fun setTicketsAlarm(ticketRepository: TicketRepository) {
        var tickets = emptyList<Ticket>()
        thread {
            tickets = ticketRepository.findAll()
        }.join()
        tickets.forEach { view.setTicketAlarm(it) }
    }

    override fun cancelTicketsAlarm(ticketRepository: TicketRepository) {
        Thread {
            val tickets = ticketRepository.findAll()
            tickets.forEach { view.cancelTicketAlarm(it) }
        }.start()
    }
}

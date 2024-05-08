package woowacourse.movie.feature.setting

import woowacourse.movie.data.TicketRepository

class SettingPresenter(private val view: SettingContract.View) : SettingContract.Presenter {
    override fun setTicketsAlarm(ticketRepository: TicketRepository) {
        Thread {
            val tickets = ticketRepository.findAll()
            tickets.forEach { view.setTicketAlarm(it) }
        }.start()
    }

    override fun cancelTicketsAlarm(ticketRepository: TicketRepository) {
        Thread {
            val tickets = ticketRepository.findAll()
            tickets.forEach { view.cancelTicketAlarm(it) }
        }.start()
    }
}

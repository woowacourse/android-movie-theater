package woowacourse.movie.feature.setting

import woowacourse.movie.data.TicketRepository
import java.time.LocalDateTime

class SettingPresenter(
    private val view: SettingContract.View
) : SettingContract.Presenter {
    override fun setTicketsAlarm(ticketRepository: TicketRepository) {
        val tickets = ticketRepository.findAll()
        tickets.forEach { ticket ->
            val screeningDateTime =
                LocalDateTime.of(ticket.screeningDate, ticket.screeningTime)
                    .minusMinutes(TICKET_ALARM_INTERVAL_MINUTE)
            view.setTicketAlarm(screeningDateTime, ticket.id.toInt())
        }
    }

    override fun cancelTicketsAlarm(ticketRepository: TicketRepository) {
        val tickets = ticketRepository.findAll()
        tickets.forEach {
            view.cancelTicketAlarm(it.id.toInt())
        }
    }

    companion object {
        private const val TICKET_ALARM_INTERVAL_MINUTE = 30L
    }
}

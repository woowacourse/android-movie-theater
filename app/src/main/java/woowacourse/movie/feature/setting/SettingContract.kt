package woowacourse.movie.feature.setting

import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.util.BasePresenter

interface SettingContract {
    interface View {
        fun setTicketAlarm(ticket: Ticket)

        fun cancelTicketAlarm(ticket: Ticket)
    }

    interface Presenter : BasePresenter {
        fun setTicketsAlarm(ticketRepository: TicketRepository)

        fun cancelTicketsAlarm(ticketRepository: TicketRepository)
    }
}

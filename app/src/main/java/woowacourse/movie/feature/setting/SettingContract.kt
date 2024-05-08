package woowacourse.movie.feature.setting

import woowacourse.movie.data.TicketRepository
import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.util.BasePresenter
import java.time.LocalDateTime

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

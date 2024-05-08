package woowacourse.movie.feature.setting

import woowacourse.movie.data.TicketRepository
import woowacourse.movie.util.BasePresenter
import java.time.LocalDateTime

interface SettingContract {
    interface View {
        fun setTicketAlarm(
            localDateTime: LocalDateTime,
            requestCode: Int,
        )

        fun cancelTicketAlarm(requestCode: Int)
    }

    interface Presenter : BasePresenter {
        fun setTicketsAlarm(ticketRepository: TicketRepository)

        fun cancelTicketsAlarm(ticketRepository: TicketRepository)
    }
}

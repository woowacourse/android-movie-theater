package woowacourse.movie.ticket.contract

import woowacourse.movie.ticket.model.DbTicket
import java.io.Serializable

interface MovieTicketContract {
    interface View {
        val presenter: Presenter

        fun showTicketView(dbTicket: DbTicket)

        fun makeAlarm(ticket: DbTicket)
    }

    interface Presenter {
        fun setTicketInfo()

        fun storeTicketData(ticket: Serializable?, movieId: Long?)
    }
}

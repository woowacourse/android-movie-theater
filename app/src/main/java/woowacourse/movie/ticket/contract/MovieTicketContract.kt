package woowacourse.movie.ticket.contract

import woowacourse.movie.ticket.model.TicketEntity
import java.io.Serializable

interface MovieTicketContract {
    interface View {
        val presenter: Presenter

        fun showTicketView(ticketEntity: TicketEntity)

        fun makeAlarm(ticket: TicketEntity)
    }

    interface Presenter {
        fun setTicketInfo()

        fun storeTicketData(ticket: Serializable?, movieId: Long?)
    }
}

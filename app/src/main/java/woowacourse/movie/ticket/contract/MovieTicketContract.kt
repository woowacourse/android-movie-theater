package woowacourse.movie.ticket.contract

import woowacourse.movie.ticket.model.DbTicket
import java.io.Serializable

interface MovieTicketContract {
    interface View {
        val presenter: Presenter

        fun showTicketView(
            movieTitle: String,
            screeningDate: String,
            screeningTime: String,
            seatsCount: Int,
            seats: String,
            theater: String,
            moviePrice: Int,
        )

        fun storeTicketInDb(ticket: DbTicket)
    }

    interface Presenter {
        fun setTicketInfo()

        fun storeTicketData(ticket: Serializable?)

        fun storeTicketInDb()
    }
}

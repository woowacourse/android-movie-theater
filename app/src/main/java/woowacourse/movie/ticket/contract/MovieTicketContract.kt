package woowacourse.movie.ticket.contract

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.ticket.model.DbTicket

interface MovieTicketContract {
    interface View {
        val presenter: Presenter

        fun showTicketView(
            movieTitle: String,
            screeningDate: String,
            screeningTime: String,
            seatsCount: Count,
            seats: List<Seat>,
            theater: String,
            moviePrice: Int,
        )

        fun storeTicketsIntDb(ticket: DbTicket)
    }

    interface Presenter {
        fun setTicketInfo()

        fun storeTicketData(
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            seatsCount: Count,
            seats: List<Seat>,
            theaterId: Long,
            price: Int,
        )

        fun saveTicketIntDb()
        fun storeDbTickets(tickets: List<DbTicket>)
    }
}

package woowacourse.movie.ticket.contract

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat

interface MovieTicketContract {
    interface View {
        val presenter: Presenter

        fun showTicketView(
            movieTitle: String,
            moviePrice: Int,
            ticketCount: Int,
            seats: List<Seat>,
            theater: String,
            screeningDate: String,
            screeningTime: String,
        )
    }

    interface Presenter {
        fun setTicketInfo()

        fun storeTicketData(
            theaterId: Long,
            ticketCount: Count,
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            price: Int,
            seats: List<Seat>
        )
    }
}

package woowacourse.movie.ticket.contract

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat

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
    }
}

package woowacourse.movie.view.movieTicket

import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.model.seat.SeatModel

interface MovieTicketContract {
    interface View {
        var presenter: Presenter

        fun initMovieTicketModel()

        fun setBackPressedCallback()

        fun setTextMovieTitle(title: String)

        fun setTextMovieDate(ticketTime: TicketTimeModel)

        fun setTextMovieSeats(seats: Set<SeatModel>, theater: String)

        fun setTextMovieTicketPrice(price: PriceModel)
    }

    interface Presenter {
        fun setupTicketInfo(ticketModel: MovieTicketModel)
    }
}

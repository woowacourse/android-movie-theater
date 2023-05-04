package woowacourse.movie.contract.movieticket

import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.model.seat.SeatModel

interface MovieTicketContract {
    interface View {
        var presenter: Presenter

        fun initMovieTicketModel()

        fun setBackPressedCallback()

        fun setTextMovieTitle(title: String)

        fun setTextMovieDate(ticketTime: TicketTimeModel)

        fun setTextMovieSeats(seats: Set<SeatModel>)

        fun setTextMovieTicketPrice(price: PriceModel)
    }

    interface Presenter {
        fun setupTicketInfo(ticketModel: MovieTicketModel)
    }
}

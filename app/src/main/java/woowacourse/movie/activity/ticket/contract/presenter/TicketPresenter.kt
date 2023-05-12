package woowacourse.movie.activity.ticket.contract.presenter

import woowacourse.movie.activity.ticket.contract.TicketContract
import woowacourse.movie.dto.movie.BookingMovieUIModel

class TicketPresenter(val view: TicketContract.View) :
    TicketContract.Presenter {
    override fun loadData(data: BookingMovieUIModel) {
        view.showTicketMovieInfo(data.movieTitle, data.date, data.time)
        view.showTicketInfo(data.ticketCount, data.seats, data.theaterName)
        view.showTicketPrice(data.seats, data.date, data.time)
    }
}

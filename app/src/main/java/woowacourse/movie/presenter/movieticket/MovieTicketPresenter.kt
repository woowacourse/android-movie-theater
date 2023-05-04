package woowacourse.movie.presenter.movieticket

import woowacourse.movie.contract.movieticket.MovieTicketContract
import woowacourse.movie.ui.model.MovieTicketModel

class MovieTicketPresenter(private val view: MovieTicketContract.View) :
    MovieTicketContract.Presenter {
    private lateinit var ticketModel: MovieTicketModel

    override fun setupTicketInfo(ticketModel: MovieTicketModel) {
        this.ticketModel = ticketModel

        view.setTextMovieTitle(ticketModel.title)
        view.setTextMovieDate(ticketModel.time)
        view.setTextMovieSeats(ticketModel.seats)
        view.setTextMovieTicketPrice(ticketModel.price)
    }
}

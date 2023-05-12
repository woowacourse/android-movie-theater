package woowacourse.movie.view.movieTicket

import woowacourse.movie.model.MovieTicketModel

class MovieTicketPresenter(private val view: MovieTicketContract.View) :
    MovieTicketContract.Presenter {
    private lateinit var ticketModel: MovieTicketModel

    override fun setupTicketInfo(ticketModel: MovieTicketModel) {
        this.ticketModel = ticketModel

        view.setTextMovieTitle(ticketModel.title)
        view.setTextMovieDate(ticketModel.time)
        view.setTextMovieSeats(ticketModel.seats, ticketModel.theater)
        view.setTextMovieTicketPrice(ticketModel.price)
    }
}

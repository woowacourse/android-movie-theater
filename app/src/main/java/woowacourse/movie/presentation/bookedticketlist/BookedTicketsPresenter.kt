package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.data.bookedticket.BookedTicketsData
import woowacourse.movie.data.movie.MovieData
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsPresenter(
    private val view: BookedTicketsContract.View,
    private val bookedTicketsData: BookedTicketsData,
    private val movieData: MovieData,
) :
    BookedTicketsContract.Presenter {

    override fun setBookedTickets() {
        view.setBookedTicketsAdapter(bookedTicketsData.getTickets())
    }

    override fun getMovieModel(ticketModel: TicketModel) =
        movieData.findMovieById(ticketModel.movieId).toPresentation()
}

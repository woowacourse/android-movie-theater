package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.data.bookedticket.BookedTicketsData
import woowacourse.movie.data.movie.DefaultMovieData
import woowacourse.movie.data.movie.MovieData
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsPresenter(
    private val view: BookedTicketsContract.View,
    private val bookedTicketsData: BookedTicketsData,
    private val movieData: MovieData,
) :
    BookedTicketsContract.Presenter {

    override fun requestBookedTickets() {
        view.setBookedTickets(bookedTicketsData.getTickets())
    }

    override fun getMovieModel(ticketModel: TicketModel): MovieModel {
        val movie =
            movieData.findMovieById(ticketModel.movieId) ?: return DefaultMovieData.defaultMovie
        return movie.toPresentation()
    }
}

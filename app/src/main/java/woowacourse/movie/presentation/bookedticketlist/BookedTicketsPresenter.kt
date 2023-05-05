package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.data.BookedTicketsData
import woowacourse.movie.data.MovieData
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketsPresenter(private val view: BookedTicketsContract.View) :
    BookedTicketsContract.Presenter {

    private lateinit var tickets: List<TicketModel>

    override fun changeTickets() {
        tickets = BookedTicketsData.tickets.toList()
        view.setBookedTicketsAdapter(BookedTicketsData.tickets)
    }

    override fun getMovieModel(ticketModel: TicketModel) =
        MovieData.findMovieById(ticketModel.movieId).toPresentation()
}

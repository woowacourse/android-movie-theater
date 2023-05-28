package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.model.data.storage.BookedTicketStorage
import woowacourse.movie.model.data.storage.MovieStorage
import woowacourse.movie.presentation.model.TicketModel

class BookedTicketPresenter(
    private val movieStorage: MovieStorage,
    private val bookedTicketStorage: BookedTicketStorage
) :
    BookedTicketsContract.Presenter {
    override fun getMovieById(movieId: Long): Movie = movieStorage.getMovieById(movieId)
    override fun getBookedTickets(): List<TicketModel> = bookedTicketStorage.getBookedTickets()
}

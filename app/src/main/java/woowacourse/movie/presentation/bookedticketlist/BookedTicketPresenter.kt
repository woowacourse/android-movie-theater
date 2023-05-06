package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.model.data.storage.MovieStorage

class BookedTicketPresenter(private val movieStorage: MovieStorage) :
    BookedTicketsContract.Presenter {
    override fun getMovieById(movieId: Long): Movie = movieStorage.getMovieById(movieId)
}

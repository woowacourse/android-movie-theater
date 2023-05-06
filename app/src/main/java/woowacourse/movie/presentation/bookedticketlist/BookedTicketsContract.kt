package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.domain.model.tools.Movie

interface BookedTicketsContract {
    interface Adapter {
        val presenter: Presenter
    }

    interface Presenter {
        fun getMovieById(movieId: Long): Movie
    }
}

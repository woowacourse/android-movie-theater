package woowacourse.movie.presentation.bookedticketlist

import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.presentation.model.TicketModel

interface BookedTicketsContract {
    interface View {
        val presenter: Presenter
    }

    interface Adapter {
        val presenter: Presenter
    }

    interface Presenter {
        fun getMovieById(movieId: Long): Movie

        fun getBookedTickets(): List<TicketModel>
    }
}

package woowacourse.movie.feature.result

import woowacourse.movie.data.movie.dto.Movie
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.util.BasePresenter

interface MovieResultContract {
    interface View {
        fun displayTicket(
            ticket: Ticket,
            movie: Movie,
        )

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadTicket(ticketId: Long)
    }
}

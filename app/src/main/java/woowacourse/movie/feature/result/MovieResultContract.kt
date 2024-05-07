package woowacourse.movie.feature.result

import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.util.BasePresenter

interface MovieResultContract {
    interface View {
        fun displayTicket(ticket: Ticket)

        fun showToastInvalidMovieIdError(throwable: Throwable)
    }

    interface Presenter : BasePresenter {
        fun loadTicket(
            movieId: Long,
            screeningDate: String,
            screeningTime: String,
            reservationCount: Int,
            selectedSeats: String,
            theaterName: String,
        )
    }
}

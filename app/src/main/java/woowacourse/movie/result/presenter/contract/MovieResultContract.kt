package woowacourse.movie.result.presenter.contract

import android.content.Context
import woowacourse.movie.model.MovieTicket

interface MovieResultContract {
    interface View {
        fun displayMovieTicket(movieTicketData: MovieTicket?)
    }

    interface Presenter {
        fun loadMovieTicket(
            context: Context,
            ticketId: Long,
        )

        fun registrationMovieNotification(
            context: Context,
            ticketId: Long,
        )
    }
}

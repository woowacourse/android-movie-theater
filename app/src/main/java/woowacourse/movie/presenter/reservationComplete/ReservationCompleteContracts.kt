package woowacourse.movie.presenter.reservationComplete

import android.content.Context
import woowacourse.movie.model.ticket.MovieTicket

interface ReservationCompleteContracts {
    interface View {
        fun showMovieTicket(movieTicket: MovieTicket)
    }

    interface Presenter {
        fun updateTicketData(movieTicket: MovieTicket)

        fun updateTicketData2(
            movieTicket: MovieTicket,
            context: Context,
        )
    }
}

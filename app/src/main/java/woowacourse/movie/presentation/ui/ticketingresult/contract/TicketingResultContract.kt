package woowacourse.movie.presentation.ui.ticketingresult.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice

interface TicketingResultContract {
    interface View : BaseContract.View {
        fun showMainScreen(reservation: Reservation, fromMainScreen: Boolean)
        fun showTicket(ticket: Ticket)
        fun showTicketingDate(date: MovieDate, time: MovieTime)
        fun showMovieTitle(title: String)
        fun showSeats(seats: PickedSeats)
        fun showTicketPrice(ticketPrice: TicketPrice)
        fun showTheaterName(name: String)
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun onShowMainScreen()
    }
}

package woowacourse.movie.ui.seat.presenter

import android.content.Intent
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.common.BaseView
import woowacourse.movie.ui.seat.uimodel.SelectedSeatsModel

interface SeatContractor {
    interface View : BaseView<Presenter> {
        fun putSelectedSeats(selectedSeats: SelectedSeatsModel)
        fun makeDialog(onClick: () -> Unit)
        fun moveToTicketActivity(ticket: MovieTicketModel)
    }

    interface Presenter {
        fun getBookingInfo(intent: Intent)
        fun getSelectedSeats()
        fun getMovieTicket()
    }
}

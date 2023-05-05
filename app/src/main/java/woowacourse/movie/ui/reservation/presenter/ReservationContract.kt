package woowacourse.movie.ui.reservation.presenter

import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.common.BaseView

interface ReservationContract {
    interface View : BaseView<Presenter> {
        var reservationTicket: List<MovieTicketModel>
        fun setTextOnEmptyState(isEmpty: Boolean)
    }

    interface Presenter {
        fun getReservationTickets()
        fun isEmptyMovieReservation()
    }
}

package woowacourse.movie.ui.reservation.presenter

import woowacourse.movie.ui.common.BaseView
import woowacourse.movie.ui.reservation.adapter.ReservationAdapter

interface ReservationContract {
    interface View : BaseView<Presenter> {
        var reservationAdapter: ReservationAdapter
        fun setTextOnEmptyState(isEmpty: Boolean)
    }

    interface Presenter {
        fun getReservationTickets()
        fun isEmptyMovieReservation()
    }
}

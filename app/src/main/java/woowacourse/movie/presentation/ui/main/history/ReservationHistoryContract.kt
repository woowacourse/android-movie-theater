package woowacourse.movie.presentation.ui.main.history

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView

interface ReservationHistoryContract {
    interface View : BaseView {
        fun showReservations(reservations: List<Reservation>)

        fun navigateToReservation(reservationId: Long)
    }

    interface Presenter : BasePresenter, ReservationHistoryActionHandler {
        fun loadReservations()
    }
}

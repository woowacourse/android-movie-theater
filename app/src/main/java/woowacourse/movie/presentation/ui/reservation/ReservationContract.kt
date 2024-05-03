package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.presentation.base.BaseView

interface ReservationContract {
    interface View : BaseView {
        fun showReservation(
            reservation: Reservation,
            theaterName: String,
        )

        fun back()
    }

    interface Presenter {
        fun loadReservation(id: Int)
    }
}

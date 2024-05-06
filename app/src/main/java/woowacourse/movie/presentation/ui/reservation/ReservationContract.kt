package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.presentation.base.BaseView

interface ReservationContract {
    interface View : BaseView {
        fun showReservation(
            reservationModel: ReservationModel,
            theaterName: String,
        )

        fun finishReservation()
    }

    interface Presenter {
        fun loadReservation(id: Int)
    }
}

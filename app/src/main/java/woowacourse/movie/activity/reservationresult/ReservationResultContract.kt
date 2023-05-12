package woowacourse.movie.activity.reservationresult

import woowacourse.movie.view.data.ReservationViewData

interface ReservationResultContract {
    interface View {
        fun setUpReservation(reservationViewData: ReservationViewData)
    }

    interface Presenter {
        fun setUpReservation(
            reservationViewData: ReservationViewData,
        )
    }
}

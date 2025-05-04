package woowacourse.movie.view.reservation.complete

import woowacourse.movie.view.model.ReservationInfo

interface ReservationCompleteContract {
    interface View {
        fun showErrorDialog()

        fun showReservationInfo(reservationInfo: ReservationInfo)
    }

    interface Presenter {
        fun fetchData(reservationInfo: ReservationInfo?)
    }
}

package woowacourse.movie.view.reservation.history

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationHistoryContract {
    interface View {
        fun showReservationHistory(reservationInfo: ReservationInfo)
    }

    interface Presenter {
        fun fetchReservationHistory(reservationInfo: ReservationInfo)
    }
}

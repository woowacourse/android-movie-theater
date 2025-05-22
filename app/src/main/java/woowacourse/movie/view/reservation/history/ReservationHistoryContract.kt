package woowacourse.movie.view.reservation.history

import woowacourse.movie.domain.model.ReservationInfo

interface ReservationHistoryContract {
    interface View {
        fun showReservationHistory(reservationInfos: List<ReservationInfo>)
    }

    interface Presenter {
        fun fetchReservationHistory()
    }
}

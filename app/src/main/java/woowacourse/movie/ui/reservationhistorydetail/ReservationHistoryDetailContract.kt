package woowacourse.movie.ui.reservationhistorydetail

import woowacourse.movie.db.reservationhistory.ReservationHistory

interface ReservationHistoryDetailContract {
    interface View {
        fun showReservation(reservationHistory: ReservationHistory)
    }

    interface Presenter {
        fun loadReservation(reservationId: Long)
    }
}

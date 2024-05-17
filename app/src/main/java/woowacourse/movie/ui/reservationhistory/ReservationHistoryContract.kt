package woowacourse.movie.ui.reservationhistory

import woowacourse.movie.db.reservationhistory.ReservationHistory

interface ReservationHistoryContract {
    interface View {
        fun showReservationHistories(reservationHistories: List<ReservationHistory>)
    }

    interface Presenter {
        fun loadReservationHistories()
    }
}

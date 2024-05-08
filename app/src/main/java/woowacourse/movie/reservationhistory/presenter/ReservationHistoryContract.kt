package woowacourse.movie.reservationhistory.presenter

import woowacourse.movie.data.db.ReservationHistoryEntity

interface ReservationHistoryContract {
    interface View {
        fun displayReservationHistories(reservationHistories: List<ReservationHistoryEntity>)
    }

    interface Presenter {
        fun loadReservationHistories()
    }
}

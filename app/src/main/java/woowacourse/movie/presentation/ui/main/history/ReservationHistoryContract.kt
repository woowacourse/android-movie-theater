package woowacourse.movie.presentation.ui.main.history

import woowacourse.movie.domain.model.Reservation

interface ReservationHistoryContract {
    interface View {
        fun showReservations(reservataions: List<Reservation>)
    }

    interface Presenter {
        fun fetchData()
    }
}

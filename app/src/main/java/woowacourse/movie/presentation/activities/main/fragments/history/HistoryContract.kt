package woowacourse.movie.presentation.activities.main.fragments.history

import woowacourse.movie.presentation.model.item.Reservation

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun showReservations(items: List<Reservation>)
    }

    interface Presenter {
        fun loadReservationData()
    }
}

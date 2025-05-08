package woowacourse.movie.presentation.history

import woowacourse.movie.presentation.common.model.TicketUiModel

interface ReservationHistoryContract {
    interface View {
        fun showReservationHistory(histories: List<TicketUiModel>)
    }

    interface Presenter {
        fun fetchData()
    }
}

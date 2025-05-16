package woowacourse.movie.presentation.history

import woowacourse.movie.presentation.common.model.ReservationHistoryUiModel

interface ReservationHistoryContract {
    interface View {
        fun showReservationHistory(histories: List<ReservationHistoryUiModel>)
    }

    interface Presenter {
        fun fetchData()
    }
}

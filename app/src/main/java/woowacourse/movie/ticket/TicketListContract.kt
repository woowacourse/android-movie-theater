package woowacourse.movie.ticket

import woowacourse.movie.ui.model.TicketUiModel

interface TicketListContract {
    interface View {
        fun setUpReservationList(reservations: List<TicketUiModel>)
    }

    interface Presenter {
        fun initializeData()
    }
}

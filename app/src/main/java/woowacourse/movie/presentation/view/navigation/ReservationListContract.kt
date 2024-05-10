package woowacourse.movie.presentation.view.navigation

import woowacourse.movie.presentation.uimodel.TicketUiModel

interface ReservationListContract {
    interface View {
        fun showReservationTickets(tickets: List<TicketUiModel>)
    }

    interface Presenter {
        fun loadReservationTickets()
    }
}

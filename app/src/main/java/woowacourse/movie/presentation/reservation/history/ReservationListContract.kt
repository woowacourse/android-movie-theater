package woowacourse.movie.presentation.reservation.history

import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.uimodel.TicketUiModel

interface ReservationListContract {
    interface View {
        fun showReservationTickets(tickets: List<TicketUiModel>)

        fun moveToReservationResult(movieTicketUiModel: MovieTicketUiModel)
    }

    interface Presenter {
        fun loadReservationTickets()

        fun ticketInfo(ticketId: Long)
    }

    interface ItemListener {
        fun onClick(ticketId: Long)
    }
}

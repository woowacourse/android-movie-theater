package woowacourse.movie.presentation.home.reservation.result

import woowacourse.movie.presentation.common.model.TicketUiModel

interface ReservationResultContract {
    interface Presenter {
        fun fetchDate(ticket: TicketUiModel)
    }

    interface View {
        fun showScreen(
            ticket: TicketUiModel,
            cancellationTime: Int,
        )
    }
}

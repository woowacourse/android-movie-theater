package woowacourse.movie.presentation.home.reservation.result

import woowacourse.movie.presentation.common.model.TicketBundleUiModel

interface ReservationResultContract {
    interface Presenter {
        fun fetchDate(ticketBundle: TicketBundleUiModel)
    }

    interface View {
        fun showScreen(
            ticketBundle: TicketBundleUiModel,
            cancellationTime: Int,
        )
    }
}

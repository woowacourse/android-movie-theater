package woowacourse.movie.presentation.view.reservation.result

import woowacourse.movie.domain.model.cinema.ticket.TicketMachine
import woowacourse.movie.presentation.model.TicketBundleUiModel

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun fetchDate(ticketBundle: TicketBundleUiModel) {
        view.showScreen(ticketBundle, TicketMachine.CANCELLATION_TIME)
    }
}

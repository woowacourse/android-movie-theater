package woowacourse.movie.presentation.home.reservation.result

import woowacourse.movie.domain.model.cinema.ticket.TicketMachine
import woowacourse.movie.presentation.common.model.TicketBundleUiModel

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun fetchDate(ticketBundle: TicketBundleUiModel) {
        view.showScreen(ticketBundle, TicketMachine.CANCELLATION_TIME)
    }
}

package woowacourse.movie.presentation.home.reservation.result

import woowacourse.movie.domain.model.ticketing.TicketMachine
import woowacourse.movie.presentation.common.model.TicketUiModel

class ReservationResultPresenter(
    private val view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun fetchData(ticket: TicketUiModel) {
        view.showScreen(ticket, TicketMachine.CANCELLATION_TIME)
    }
}

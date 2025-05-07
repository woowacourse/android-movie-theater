package woowacourse.movie.view.reservation.result

import woowacourse.movie.domain.model.Ticket

class ReservationResultPresenter(
    private var view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun loadReservationInfo(ticket: Ticket?) {
        ticket?.let {
            view.showReservationResult(ticket)
            return
        }
    }
}

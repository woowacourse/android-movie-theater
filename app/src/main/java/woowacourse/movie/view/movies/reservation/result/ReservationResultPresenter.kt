package woowacourse.movie.view.movies.reservation.result

import woowacourse.movie.domain.model.Ticket

class ReservationResultPresenter(
    private var view: ReservationResultContract.View,
) : ReservationResultContract.Presenter {
    override fun loadReservationInfo(ticket: Ticket) {
        view.showReservationResult(ticket)
    }
}

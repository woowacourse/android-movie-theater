package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.data.storage.ReservationStorage

class ReservationCompletePresenter(
    private val view: ReservationCompleteContracts.View,
    private val reservationStorage: ReservationStorage,
) : ReservationCompleteContracts.Presenter {
    override fun fetchTicketData(reservationDetailId: Long) {
        reservationStorage.getMovieTicket(reservationDetailId) {
            view.showMovieTicket(
                it ?: return@getMovieTicket,
            )
        }
    }
}

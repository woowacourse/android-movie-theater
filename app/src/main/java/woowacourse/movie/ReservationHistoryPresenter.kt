package woowacourse.movie

import woowacourse.movie.contract.reservation.ReservationHistoryContract

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistories() {
        view.updateReservationHistories()
    }
}

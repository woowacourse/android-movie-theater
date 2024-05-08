package woowacourse.movie.reservationhistory.presenter

import woowacourse.movie.data.ReservationHistoryEntity

class ReservationHistoryPresenter(private val reservationHistoryView: ReservationHistoryContract.View) :
    ReservationHistoryContract.Presenter {
    private val reservationHistoryEntities: MutableList<ReservationHistoryEntity> = mutableListOf()

    override fun loadReservationHistories() {
        reservationHistoryView.displayReservationHistories(reservationHistoryEntities)
    }
}

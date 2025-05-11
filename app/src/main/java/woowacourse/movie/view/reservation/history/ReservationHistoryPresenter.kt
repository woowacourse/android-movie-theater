package woowacourse.movie.view.reservation.history

import woowacourse.movie.db.ReservationInfoDao
import woowacourse.movie.domain.model.ReservationInfo

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationInfoDao: ReservationInfoDao,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistory(reservationInfo: ReservationInfo) {
    }
}

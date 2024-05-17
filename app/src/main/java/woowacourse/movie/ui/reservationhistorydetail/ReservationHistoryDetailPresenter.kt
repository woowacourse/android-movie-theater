package woowacourse.movie.ui.reservationhistorydetail

import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import kotlin.concurrent.thread

class ReservationHistoryDetailPresenter(
    private val view: ReservationHistoryDetailContract.View,
    private val db: ReservationHistoryDatabase,
) : ReservationHistoryDetailContract.Presenter {
    override fun loadReservation(reservationId: Long) {
        thread {
            val reservationHistory: ReservationHistory = db.reservationHistoryDao().getById(reservationId)

            view.showReservation(reservationHistory)
        }.join()
    }
}

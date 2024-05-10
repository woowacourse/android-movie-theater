package woowacourse.movie.ui.reservationhistorydetail

import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import kotlin.concurrent.thread

class ReservationHistoryDetailPresenter(
    private val view: ReservationHistoryDetailContract.View,
    private val db: ReservationHistoryDatabase,
) : ReservationHistoryDetailContract.Presenter {
    lateinit var reservationHistory: ReservationHistory

    override fun loadReservation(reservationId: Long) {
        thread {
            reservationHistory = db.reservationHistoryDao().getById(reservationId)

            view.showReservation(
                reservationHistory.reservation,
                reservationHistory.theaterName,
                reservationHistory.screeningDate,
                reservationHistory.screeningTime,
            )
        }.join()
    }
}

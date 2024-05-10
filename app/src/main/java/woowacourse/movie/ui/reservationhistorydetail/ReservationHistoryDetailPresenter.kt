package woowacourse.movie.ui.reservationhistorydetail

import woowacourse.movie.db.AppDatabase
import woowacourse.movie.db.ReservationHistory
import kotlin.concurrent.thread

class ReservationHistoryDetailPresenter(
    private val view: ReservationHistoryDetailContract.View,
    private val db: AppDatabase,
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

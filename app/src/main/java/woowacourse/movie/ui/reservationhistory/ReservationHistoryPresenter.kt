package woowacourse.movie.ui.reservationhistory

import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val db: ReservationHistoryDatabase,
) : ReservationHistoryContract.Presenter {
    override fun loadReservationHistories() {
        thread {
            val reservationHistories = db.reservationHistoryDao().getAll()
            view.showReservationHistories(reservationHistories)
        }.join()
    }
}

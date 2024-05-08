package woowacourse.movie.reservationhistory.presenter

import woowacourse.movie.data.db.ReservationHistoryDatabase
import woowacourse.movie.data.db.ReservationHistoryEntity

class ReservationHistoryPresenter(
    private val reservationHistoryView: ReservationHistoryContract.View,
    private val database: ReservationHistoryDatabase,
) :
    ReservationHistoryContract.Presenter {
    private lateinit var reservationHistoryEntities: List<ReservationHistoryEntity>

    override fun loadReservationHistories() {
        val thread =
            Thread {
                reservationHistoryEntities = database.reservationHistoryDao().findReservationHistories()
            }
        thread.start()
        thread.join()
        reservationHistoryView.displayReservationHistories(reservationHistoryEntities)
    }
}

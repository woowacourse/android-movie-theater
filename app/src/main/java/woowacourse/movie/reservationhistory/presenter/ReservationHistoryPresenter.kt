package woowacourse.movie.reservationhistory.presenter

import woowacourse.MovieApplication.Companion.database
import woowacourse.movie.data.db.ReservationHistoryEntity

class ReservationHistoryPresenter(
    private val reservationHistoryView: ReservationHistoryContract.View,
) :
    ReservationHistoryContract.Presenter {
    private lateinit var reservationHistoryEntities: List<ReservationHistoryEntity>

    override fun loadReservationHistories() {
        val thread =
            Thread {
                reservationHistoryEntities =
                    database.reservationHistoryDao().findReservationHistories()
            }
        thread.start()
        thread.join()
        reservationHistoryView.displayReservationHistories(reservationHistoryEntities)
    }
}

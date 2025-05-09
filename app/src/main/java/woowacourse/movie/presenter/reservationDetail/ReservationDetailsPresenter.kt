package woowacourse.movie.presenter.reservationDetail

import woowacourse.movie.data.db.AppDatabase
import kotlin.concurrent.thread

class ReservationDetailsPresenter(
    private val view: ReservationDetailsContracts.View,
    private val database: AppDatabase,
) : ReservationDetailsContracts.Presenter {
    override fun updateReservationDetails() {
        thread {
            val reservationDetails = database.reservationDao().getAll()
            view.showReservationDetails(reservationDetails)
        }
    }
}

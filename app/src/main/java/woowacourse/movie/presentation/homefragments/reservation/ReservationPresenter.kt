package woowacourse.movie.presentation.homefragments.reservation

import woowacourse.movie.db.ReservationDatabase
import woowacourse.movie.db.toReservation
import woowacourse.movie.model.Reservation

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationDatabase: ReservationDatabase,
) : ReservationContract.Presenter {
    private val reservationDao = reservationDatabase.reservationDao()

    override fun loadReservations() {
        val result: MutableList<Reservation> = mutableListOf()
        val thread =
            Thread {
                result.addAll(reservationDao.getAllReservation().map { it.toReservation() })
            }
        thread.start()
        thread.join()
        view.displayReservations(result)
    }
}

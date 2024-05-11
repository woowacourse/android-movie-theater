package woowacourse.movie.presentation.view.reservationdetails

import woowacourse.movie.database.Reservation
import woowacourse.movie.database.ReservationDao
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import kotlin.concurrent.thread

class ReservationDetailsPresenterImpl(
    private val view: ReservationDetailsContract.View,
    private val reservationDao: ReservationDao,
) : ReservationDetailsContract.Presenter {
    override fun loadDetailsList() {
        var reservations: List<Reservation> = listOf()
        thread(start = true) { reservations = reservationDao.getAll() }.join()
        val detailsList =
            reservations.map { reservation ->
                MovieTicketUiModel.fromReservation(reservation)
            }
        view.showDetailsList(detailsList)
    }

    override fun onDetailItemClicked(ticketId: Long) {
        var reservation: Reservation? = null
        thread { reservation = reservationDao.getTicketById(ticketId) }.join()
        view.moveToReservationResult(MovieTicketUiModel.fromReservation(reservation!!))
    }
}

package woowacourse.movie.presentation.view.reservationdetails

import android.os.Handler
import android.os.Looper
import woowacourse.movie.database.ReservationDao
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel

class ReservationDetailsPresenterImpl(
    private val view: ReservationDetailsContract.View,
    private val reservationDao: ReservationDao,
) : ReservationDetailsContract.Presenter {
    override fun loadDetailsList() {
        Thread {
            val reservations = reservationDao.getAll()
            val detailsList =
                reservations.map { reservation ->
                    MovieTicketUiModel.fromReservation(reservation)
                }

            Handler(Looper.getMainLooper()).post {
                view.showDetailsList(detailsList)
            }
        }.start()
    }

    override fun onDetailItemClicked(ticketId: Long) {
        Thread {
            val reservation = reservationDao.getTicketById(ticketId)
            Handler(Looper.getMainLooper()).post {
                view.moveToReservationResult(MovieTicketUiModel.fromReservation(reservation))
            }
        }.start()
    }
}

// thread{  }.join()

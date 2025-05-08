package woowacourse.movie.reservation

import woowacourse.movie.ui.model.TicketUiModel
import kotlin.concurrent.thread

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val repository: ReservationRepository,
) : ReservationListContract.Presenter {
    override fun initializeData() {
        thread {
            val reservations = repository.getAllReservations()
            (view as? ReservationListFragment)?.requireActivity()?.runOnUiThread {
                view.showReservationList(reservations)
            }
        }
    }

    override fun setReservations(reservation: TicketUiModel) {
        view.startBookingCompleteActivity(reservation)
    }
}

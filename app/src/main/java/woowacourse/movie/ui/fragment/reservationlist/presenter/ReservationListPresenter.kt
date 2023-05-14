package woowacourse.movie.ui.fragment.reservationlist.presenter

import woowacourse.movie.data.entity.Reservations
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.ui.fragment.reservationlist.ReservationListContract

class ReservationListPresenter(
    private val repository: ReservationRepository,
    private val view: ReservationListContract.View
) : ReservationListContract.Presenter {

    override fun setUpReservations() {
        val tickets = repository.getReservations()
        Reservations.restore(tickets)
    }

    override fun setReservations() {
        view.setReservations(Reservations.getAll())
    }

    override fun checkItemInsertion(count: Int) {
        val insertionCount = Reservations.getSize() - count
        if (insertionCount > 0) {
            view.notifyReservationInsertion(insertionCount)
        }
    }
}

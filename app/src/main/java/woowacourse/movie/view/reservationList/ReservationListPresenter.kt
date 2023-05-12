package woowacourse.movie.view.reservationList

import woowacourse.movie.db.ReservationDataImpl
import woowacourse.movie.entity.Reservations
import woowacourse.movie.model.MovieTicketModel

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val reservationDataImpl: ReservationDataImpl
) :
    ReservationListContract.Presenter {
    private var reservations = Reservations.getAll()
    private var itemSize = 0

    override fun loadReservations() {
        reservations = getReservationList()
        itemSize = reservations.size
        view.setReservationView(reservations)
    }

    override fun setItemsInsertion() {
        reservations = getReservationList()

        val diffSize = reservations.size - itemSize
        if (diffSize > 0) {
            view.updateReservationViewItem(itemSize, diffSize)
            itemSize = reservations.size
        }
    }

    private fun getReservationList(): List<MovieTicketModel> = reservationDataImpl.getReservations()
}

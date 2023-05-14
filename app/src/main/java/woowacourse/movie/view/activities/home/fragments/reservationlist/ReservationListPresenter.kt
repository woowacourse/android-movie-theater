package woowacourse.movie.view.activities.home.fragments.reservationlist

import woowacourse.movie.repository.ReservationRepository1

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val reservationRepository1: ReservationRepository1
) :
    ReservationListContract.Presenter {

    override fun loadReservations() {
        val reservations = reservationRepository1.findAll()
        view.setReservationList(reservations.map(ReservationListViewItemUIState::from))
    }
}

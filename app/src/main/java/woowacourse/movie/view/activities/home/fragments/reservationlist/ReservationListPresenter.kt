package woowacourse.movie.view.activities.home.fragments.reservationlist

import woowacourse.movie.repository.ReservationRepository

class ReservationListPresenter(private val view: ReservationListContract.View) :
    ReservationListContract.Presenter {

    override fun loadReservations() {
        val reservations = ReservationRepository.findAll()
        view.setReservationList(reservations.map(ReservationListViewItemUIState::from))
    }
}

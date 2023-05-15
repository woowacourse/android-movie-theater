package woowacourse.movie.view.activities.home.fragments.reservationlist

import woowacourse.movie.repository.ReservationRepository

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val reservationRepository: ReservationRepository
) :
    ReservationListContract.Presenter {

    override fun loadReservations() {
        val reservations = reservationRepository.findAll()
        view.setReservationList(reservations.map(ReservationListViewItemUIState::from))
    }
}

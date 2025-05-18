package woowacourse.movie.presenter.reservationDetail

import woowacourse.movie.data.storage.ReservationStorage

class ReservationDetailsPresenter(
    private val view: ReservationDetailsContracts.View,
    private val reservationStorage: ReservationStorage,
) : ReservationDetailsContracts.Presenter {
    override fun fetchReservationDetails() {
        reservationStorage.getAllMovieTickets {
            view.showReservationDetails(it)
        }
    }
}

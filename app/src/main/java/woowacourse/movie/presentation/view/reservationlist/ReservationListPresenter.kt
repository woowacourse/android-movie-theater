package woowacourse.movie.presentation.view.reservationlist

import woowacourse.movie.domain.ReservationFetcher
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toPresentation

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val reservationFetcher: ReservationFetcher,
) : ReservationListContract.Presenter {
    override fun fetchReservations() {
        reservationFetcher.fetchAll { reservations ->
            view.showReservations(reservations.map { it.toPresentation() })
        }
    }

    override fun reservationSelected(reservation: ReservationInfoUiModel) {
        view.navigateToComplete(reservation)
    }
}

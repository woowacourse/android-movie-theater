package woowacourse.movie.presentation.view.reservationlist

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toPresentation

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val reservationRepository: ReservationRepository,
) : ReservationListContract.Presenter {
    override fun fetchReservations() {
        reservationRepository.getAllReservations { reservations ->
            view.showReservations(reservations.map { it.toPresentation() })
        }
    }

    override fun reservationSelected(reservation: ReservationInfoUiModel) {
        view.navigateToComplete(reservation)
    }
}

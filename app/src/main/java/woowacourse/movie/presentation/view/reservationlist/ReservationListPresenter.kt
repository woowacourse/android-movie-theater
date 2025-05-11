package woowacourse.movie.presentation.view.reservationlist

import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.toPresentation
import kotlin.concurrent.thread

class ReservationListPresenter(
    val view: ReservationListContract.View,
    private val provider: ReservationRepository,
) : ReservationListContract.Presenter {
    override fun fetchReservations() {
        thread {
            val reservations = provider.getAllReservations()
            view.showReservations(reservations.map { it.toPresentation() })
        }
    }

    override fun reservationSelected(reservation: ReservationInfoUiModel) {
        view.navigateToComplete(reservation)
    }
}

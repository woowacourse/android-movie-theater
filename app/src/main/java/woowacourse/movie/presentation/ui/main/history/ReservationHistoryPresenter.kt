package woowacourse.movie.presentation.ui.main.history

import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val repository: ReservationRepository,
) : ReservationHistoryContract.Presenter {
    override fun loadReservations() {
        thread {
            repository.getReservations().onSuccess { reservations ->
                view.showReservations(reservations)
            }.onFailure { e ->
                view.showToastMessage(e)
            }
        }
    }

    override fun onReservationClick(id: Long) {
        view.navigateToReservation(id)
    }
}

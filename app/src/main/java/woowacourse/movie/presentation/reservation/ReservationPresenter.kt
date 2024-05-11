package woowacourse.movie.presentation.reservation

import woowacourse.movie.data.MovieRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val repository: MovieRepository,
    private val view: ReservationContract.View
) {
    fun loadReservations() {
        thread {
            repository.loadReservedMovies().onSuccess {
                view.showReservations(it)
            }.onFailure {
                view.showError()
            }
        }.join()
    }

    fun findReservation(reservationId: Long) {
        view.navigateToConfirmPurchaseView(reservationId)
    }
}
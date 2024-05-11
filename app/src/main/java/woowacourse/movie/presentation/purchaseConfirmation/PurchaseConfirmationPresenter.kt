package woowacourse.movie.presentation.purchaseConfirmation

import woowacourse.movie.data.MovieRepository
import kotlin.concurrent.thread

class PurchaseConfirmationPresenter(
    private val repository: MovieRepository,
    private val view: PurchaseConfirmationContract.View,
) : PurchaseConfirmationContract.Presenter {
    override fun loadReservation(reservationId: Long) {
        thread {
            repository.loadReservedMovie(reservationId).onSuccess {
                view.showReservation(it)
            }.onFailure {
                view.showError()
            }
        }.join()
    }
}

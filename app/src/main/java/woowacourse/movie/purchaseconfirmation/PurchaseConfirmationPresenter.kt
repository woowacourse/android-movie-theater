package woowacourse.movie.purchaseconfirmation

import woowacourse.movie.purchaseconfirmation.uimodel.toReservationResultUiModel
import woowacourse.movie.repository.MovieRepository

class PurchaseConfirmationPresenter(
    private val repository: MovieRepository,
    private val view: PurchaseConfirmationContract.View,
) : PurchaseConfirmationContract.Presenter {
    override fun loadReservationResult(reservationId: Long) {
        val reservationResult = repository.reservationById(reservationId)
        val theater = repository.theaterById(reservationResult.theaterId)
        view.showResult(reservationResult.toReservationResultUiModel(theater.name))
    }
}

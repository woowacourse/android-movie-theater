package woowacourse.movie.purchaseconfirmation

import woowacourse.movie.purchaseconfirmation.uimodel.toReservationResultUiModel
import woowacourse.movie.repository.MovieRepository

class PurchaseConfirmationPresenter(
    private val repository: MovieRepository,
    private val view: PurchaseConfirmationContract.View,
) : PurchaseConfirmationContract.Presenter {
    override fun loadReservationResult(reservationId: Long) {
        runCatching {
            val reservation =
                repository.reservationById(reservationId) ?: error(RESERVATION_CHECK_ERROR)
            val theater =
                repository.theaterById(reservation.theaterId) ?: error(THEATER_CHECK_ERROR)
            reservation.toReservationResultUiModel(theater.name)
        }.onSuccess {
            view.showResult(it)
        }.onFailure {
            // view.showError()
        }
    }

    companion object {
        private const val RESERVATION_CHECK_ERROR = "예매 내역을 찾을 수 없었습니다"
        private const val THEATER_CHECK_ERROR = "예매한 영화관을 찾을 수 없었습니다"
    }
}

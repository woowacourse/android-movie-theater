package woowacourse.movie.purchaseconfirmation

import woowacourse.movie.purchaseconfirmation.uimodel.toPurchaseConfirmationUiModel
import woowacourse.movie.usecase.FetchReservationWithIdUseCase
import java.util.concurrent.FutureTask

class PurchaseConfirmationPresenter(
    private val view: PurchaseConfirmationContract.View,
    private val fetchReservationWithIdUseCase: FetchReservationWithIdUseCase,
) : PurchaseConfirmationContract.Presenter {
    override fun loadReservationResult(reservationId: Long) {
        val task = FutureTask { fetchReservationWithIdUseCase(reservationId) }
        Thread(task).start()
        task.get().onSuccess {
            val uiModel = it.toPurchaseConfirmationUiModel()
            view.showResult(uiModel)
        }.onFailure {
            // view.showError()
        }
    }
}

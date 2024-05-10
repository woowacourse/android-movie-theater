package woowacourse.movie.purchaseconfirmation

import android.util.Log
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
            Log.d("fsa", "$it")
            val uiModel = it.toPurchaseConfirmationUiModel()
            view.showResult(uiModel)
        }.onFailure {
            Log.d("nope", it.toString())
            // view.showError()
        }
        /*
        runCatching {
            //val reservation =
                //repository.reservationById(reservationId) ?: error(RESERVATION_CHECK_ERROR)

            reservation.toPurchaseConfirmationUiModel()
        }.onSuccess {
        }.onFailure {
        }
         */
    }
}

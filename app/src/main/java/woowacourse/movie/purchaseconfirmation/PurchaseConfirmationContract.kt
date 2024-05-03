package woowacourse.movie.purchaseconfirmation

import woowacourse.movie.purchaseconfirmation.uimodel.PurchaseConfirmationUiModel

interface PurchaseConfirmationContract {
    interface View {
        fun showResult(reservationResult: PurchaseConfirmationUiModel)
    }

    interface Presenter {
        fun loadReservationResult(reservationId: Long)
    }
}

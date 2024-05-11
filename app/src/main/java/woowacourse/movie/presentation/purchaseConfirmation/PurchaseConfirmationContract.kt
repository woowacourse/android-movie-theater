package woowacourse.movie.presentation.purchaseConfirmation

import woowacourse.movie.model.Reservation

interface PurchaseConfirmationContract {
    interface View {
        fun showReservation(reservation: Reservation)

        fun showError()
    }

    interface Presenter {
        fun loadReservation(reservationId: Long)
    }
}
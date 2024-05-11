package woowacourse.movie.presentation.reservation

import woowacourse.movie.model.Reservation

interface ReservationContract {
    interface View {
        fun showReservations(reservations: List<Reservation>)

        fun navigateToConfirmPurchaseView(reservationId: Long)

        fun showError()
    }
}

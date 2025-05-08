package woowacourse.movie.reservation

import woowacourse.movie.ui.model.TicketUiModel

interface ReservationListContract {
    interface View {
        fun showReservationList(reservations: List<TicketUiModel>)

        fun showToast(message: String)

        fun startBookingCompleteActivity(reservation: TicketUiModel)
    }

    interface Presenter {
        fun initializeData()

        fun setReservations(reservation: TicketUiModel)
    }
}

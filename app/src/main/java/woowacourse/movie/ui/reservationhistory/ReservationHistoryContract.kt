package woowacourse.movie.ui.reservationhistory

import woowacourse.movie.data.model.ReservationTicket

interface ReservationHistoryContract {
    interface View {
        fun showAllReservationHistory(reservations: List<ReservationTicket>)

        fun showAllReservationHistoryError(throwable: Throwable)

        fun showReservationHistoryInDetail(reservationTicketId: Int)
    }

    interface Presenter {
        fun loadAllReservationHistory()
    }
}

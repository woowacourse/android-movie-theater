package woowacourse.movie.contract.reservation

import woowacourse.movie.domain.ticket.Reservation

interface ReservationHistoryContract {
    interface Presenter {
        fun fetchReservationHistories()

        fun selectReservation(reservation: Reservation)
    }

    interface View {
        fun updateReservationHistories(reservations: List<Reservation>)

        fun showTicket(reservation: Reservation)
    }
}

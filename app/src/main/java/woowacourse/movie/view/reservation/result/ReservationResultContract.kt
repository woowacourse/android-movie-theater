package woowacourse.movie.view.reservation.result

import woowacourse.movie.domain.model.Ticket

interface ReservationResultContract {
    interface View {
        fun showReservationResult(ticket: Ticket)
    }

    interface Presenter {
        fun loadReservationInfo(ticket: Ticket?)
    }
}

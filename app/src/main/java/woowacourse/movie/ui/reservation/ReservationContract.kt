package woowacourse.movie.ui.reservation

import woowacourse.movie.data.model.ReservationTicket

interface ReservationContract {
    interface View {
        fun showReservation(reservationTicket: ReservationTicket)

        fun showReservationFail(throwable: Throwable)
    }

    interface Presenter {
        fun loadReservation()
    }
}

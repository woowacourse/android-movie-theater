package woowacourse.movie.presentation.homefragments.reservation

import woowacourse.movie.model.Reservation

interface ReservationContract {
    interface View {
        fun displayReservations(reservations: List<Reservation>)
    }

    interface Presenter {
        fun loadReservations()
    }
}

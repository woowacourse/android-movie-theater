package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.view.model.ReservationUiModel

interface ReservationListContract {
    interface View {
        fun showReservations(reservations: List<ReservationUiModel>)
    }
    interface Presenter {
        fun fetchReservations()
    }
}

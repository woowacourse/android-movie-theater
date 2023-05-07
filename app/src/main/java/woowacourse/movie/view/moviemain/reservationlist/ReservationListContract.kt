package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.view.model.ReservationUiModel

interface ReservationListContract {
    interface View {
        var presenter: Presenter
        fun showReservations(reservations: List<ReservationUiModel>)
    }
    interface Presenter {
        fun fetchReservations()
    }
}

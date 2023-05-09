package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.view.model.ReservationUiModel

interface ReservationListContract {
    interface View {
        val presenter: Presenter
        fun showReservationList(reservations: List<ReservationUiModel>)
        fun toReservationCompletedScreen(reservation: ReservationUiModel)
    }

    interface Presenter {
        fun loadReservationList()
        fun finishReservation(reservation: ReservationUiModel)
    }
}

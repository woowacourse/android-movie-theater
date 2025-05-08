package woowacourse.movie.presentation.view.reservationlist

import woowacourse.movie.presentation.model.ReservationInfoUiModel

interface ReservationListContract {
    interface View {
        fun showReservations(reservations: List<ReservationInfoUiModel>)

        fun navigateToComplete(reservation: ReservationInfoUiModel)
    }

    interface Presenter {
        fun fetchReservations()

        fun reservationSelected(reservation: ReservationInfoUiModel)
    }
}

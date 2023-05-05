package woowacourse.movie.fragment.reservationlist

import woowacourse.movie.view.model.ReservationUiModel

interface ReservationListContract {
    interface View {
        var presenter: Presenter

        fun setOnReservationItemClick(reservationUiModel: ReservationUiModel)

        fun updateReservations()
    }

    interface Presenter {
        fun getReservations(): List<ReservationUiModel>

        fun setUpClickListener()
    }
}

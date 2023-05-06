package woowacourse.movie.ui.main.reservationlist

import woowacourse.movie.data.model.uimodel.ReservationUiModel

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

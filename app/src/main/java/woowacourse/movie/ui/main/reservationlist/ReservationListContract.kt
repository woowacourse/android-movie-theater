package woowacourse.movie.ui.main.reservationlist

import woowacourse.movie.data.model.uimodel.ReservationUIModel

interface ReservationListContract {
    interface View {
        var presenter: Presenter

        fun setOnReservationItemClick(reservationUiModel: ReservationUIModel)

        fun updateReservations()
    }

    interface Presenter {
        fun getReservations(): List<ReservationUIModel>

        fun setUpClickListener()
    }
}

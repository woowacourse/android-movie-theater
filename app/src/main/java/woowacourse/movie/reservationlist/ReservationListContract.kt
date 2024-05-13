package woowacourse.movie.reservationlist

import woowacourse.movie.reservationlist.uimodel.ReservationUiModel

interface ReservationListContract {
    interface View {
        fun showContent(reservationUiModels: List<ReservationUiModel>)

        fun navigateToReservationDetail(reservationId: Long)
    }

    interface Presenter {
        fun loadContent()

        fun selectReservation(reservationId: Long)
    }
}

package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.view.model.ReservationUiModel

interface ReservationListContract {
    interface Presenter {
        fun getReservations(): List<ReservationUiModel>
    }
}

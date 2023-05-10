package woowacourse.movie.view.main.reservationlist

import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.TicketsUiModel

interface ReservationListContract {
    interface Presenter {
        fun showReservationResult(reservationUiModel: ReservationUiModel)
        fun updateReservationList()
    }

    interface View {
        fun setReservationList(reservationList: List<ReservationUiModel>)
        fun showReservationResult(
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel,
        )
    }
}

package woowacourse.movie.view.main.reservationlist

import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.model.TicketsUiModel

interface ReservationListContract {
    interface View {
        val presenter: Presenter
        fun setAdapter(reservationList: List<ReservationUiModel>)
        fun startReservationResultActivity(
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel
        )
    }

    interface Presenter {
        fun reservationItemClick(reservationUiModel: ReservationUiModel)
        fun updateReservationList()
    }
}

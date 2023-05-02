package woowacourse.movie.contract

import domain.Reservation
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TicketsUiModel

interface ReservationListContract {
    interface View {
        val presenter: Presenter
        fun setAdapter(reservationList: List<Reservation>)
        fun startReservationResultActivity(movieUiModel: MovieUiModel,ticketsUiModel: TicketsUiModel)
    }

    interface Presenter {
        fun showReservationDetail(reservation: Reservation)
        fun getReservationList()
    }
}

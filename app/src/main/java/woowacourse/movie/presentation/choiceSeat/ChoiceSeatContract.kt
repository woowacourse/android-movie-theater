package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.TicketModel

interface ChoiceSeatContract {
    interface View {
        val presenter: Presenter
        fun setPaymentAmount(amount: Int)
        fun setMovieTitle(title: String)
        fun enableConfirm()
        fun disableConfirm()
        fun confirmBookMovie(ticketModel: TicketModel)
    }

    interface Presenter {
        var isNotifiable: Boolean
        fun reserveTicketModel(reservationModel: ReservationModel)
        fun setMovieTitle(movieId: Long)
        fun addSeat(index: Int, reservationModel: ReservationModel): Boolean
        fun subSeat(index: Int, reservationModel: ReservationModel): Boolean
        fun getSeatModel(index: Int): SeatModel
    }
}

package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.model.ReservationModel
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.TicketModel

interface ChoiceSeatContract {
    interface View {
        val presenter: Presenter
        val reservation: ReservationModel
        fun setPaymentAmount(amount: Int)
        fun enableConfirm()
        fun disableConfirm()
    }

    interface Presenter {
        var isNotifiable: Boolean
        fun reserveTicketModel(): TicketModel
        fun getMovieModel(): MovieModel
        fun addSeat(index: Int): Boolean
        fun subSeat(index: Int): Boolean
        fun getSeatModel(index: Int): SeatModel
    }
}

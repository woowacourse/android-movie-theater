package woowacourse.movie.view.seat

import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.seat.SeatModel

interface SeatPickerContract {
    interface View {
        var presenter: Presenter

        fun setTicketViews(ticketModel: MovieTicketModel)

        fun setSelectedSeat(seat: SeatModel, isSelected: Boolean)

        fun setTextPrice(price: PriceModel)

        fun setEnabledDone(isEnabled: Boolean)

        fun showReservationCheckDialog(ticketModel: MovieTicketModel)
    }

    interface Presenter {
        fun setupTicket(ticketModel: MovieTicketModel)

        fun setSelectedSeat(seat: SeatModel)

        fun addSeat(seat: SeatModel)

        fun cancelSeat(seat: SeatModel)

        fun canReserveSeat(): Boolean

        fun actionReservation()

        fun getTicketOriginalModel(): MovieTicketModel
    }
}

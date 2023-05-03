package woowacourse.movie.ui.activity.seatpicker

import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.seat.SeatModel

interface SeatPickerContract {
    interface Presenter {
        fun initTicket(ticketModel: MovieTicketModel)

        fun reserveSeat(seat: SeatModel)

        fun cancelSeat(seat: SeatModel)

        fun checkSelectionDone()

        fun getTicketModelWithOriginalPrice(): MovieTicketModel

        fun getTicketModel(): MovieTicketModel
    }

    interface View {
        var presenter: Presenter

        fun applyTicketData(ticket: MovieTicketModel)

        fun setSeatReserved(seat: SeatModel)

        fun setSeatCanceled(seat: SeatModel)

        fun updatePrice(price: Int)

        fun notifyUnableToReserveMore()

        fun deactivateDoneButton()

        fun activateDoneButton()
    }
}

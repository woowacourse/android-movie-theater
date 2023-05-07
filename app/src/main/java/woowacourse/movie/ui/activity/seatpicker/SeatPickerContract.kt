package woowacourse.movie.ui.activity.seatpicker

import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.seat.SeatModel

interface SeatPickerContract {
    interface Presenter {
        fun initTicket(ticketModel: MovieTicketModel)

        fun handleSeatSelection(isReserved: Boolean, seat: SeatModel)

        fun checkSelectionDone()

        fun addReservation(db: SQLiteDatabase)

        fun getTicketModelWithOriginalPrice(): MovieTicketModel
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

        fun afterReservation(ticket: MovieTicketModel)
    }
}

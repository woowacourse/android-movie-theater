package woowacourse.movie.presentation.activities.ticketing.seatpicker

import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.item.Reservation

interface SeatPickerContract {
    interface View {
        val presenter: Presenter

        fun showTicketingConfirmDialog()

        fun showData()
        fun setDoneBtnEnabled(isEnabled: Boolean)
        fun setTotalPriceView(ticketPrice: TicketPrice)
    }

    interface Presenter {
        fun updateView()

        fun updateDoneBtnEnabled(isEnabled: Boolean)
        fun updateTotalPriceView(ticketPrice: TicketPrice)
        fun onConfirmButtonClick()

        fun calculateTotalPrice(movieDate: MovieDate, movieTime: MovieTime): TicketPrice
        fun canPick(ticket: Ticket): Boolean
        fun pick(seat: Seat)
        fun unPick(seat: Seat)
        fun getSeat(row: SeatRow, col: SeatColumn): Seat
        fun isPicked(seat: Seat): Boolean
        fun getPickedSeats(): PickedSeats
        fun setPickedSeats(pickedSeats: PickedSeats)

        fun insertData(reservation: Reservation)
    }
}

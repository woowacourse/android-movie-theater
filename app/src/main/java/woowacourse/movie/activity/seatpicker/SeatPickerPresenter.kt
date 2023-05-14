package woowacourse.movie.activity.seatpicker

import com.woowacourse.domain.seat.Seat
import com.woowacourse.domain.seat.SeatColumn
import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.seat.SeatRow
import com.woowacourse.domain.ticket.Ticket
import com.woowacourse.domain.ticket.TicketBundle
import woowacourse.movie.movie.MovieBookingInfo

class SeatPickerPresenter(
    private val view: SeatPickerContract.View,
    private val movieBookingInfo: MovieBookingInfo,
    override var seatGroup: SeatGroup = SeatGroup(),
    override var ticketBundle: TicketBundle = TicketBundle(count = movieBookingInfo.ticketCount)

) : SeatPickerContract.Presenter {

    override fun initPage() {
        view.initSeat()
        view.setMovieTitle(movieBookingInfo.title)
    }

    override fun onPickDoneButtonClicked() {
        view.showDialog()
    }

    override fun getCanAddSeat() {
        view.setPickDoneButtonColor(!seatGroup.canAdd(ticketBundle.count))
    }

    override fun onSeatClicked(index: Int) {
        val newSeat = Seat(
            SeatRow(index / SEAT_ROW_INTERVAL),
            SeatColumn(index % SEAT_ROW_INTERVAL)
        )

        if (seatGroup.seats.contains(newSeat)) {
            progressRemoveSeat(newSeat, index, movieBookingInfo)
            return
        }
        if (seatGroup.canAdd(ticketBundle.count) && !seatGroup.seats.contains(newSeat)) {
            progressAddSeat(newSeat, index, movieBookingInfo)
        }
    }

    private fun progressAddSeat(
        newSeat: Seat,
        index: Int,
        movieBookingInfo: MovieBookingInfo,
    ) {
        seatGroup = seatGroup.add(newSeat)
        ticketBundle = ticketBundle.add(Ticket(newSeat.getSeatTier().price))
        view.setSeatColor(index, isClicked = true)
        getCanAddSeat()
        view.setTicketPrice(
            ticketBundle.calculateTotalPrice(movieBookingInfo.date, movieBookingInfo.time)
        )
    }

    private fun progressRemoveSeat(
        newSeat: Seat,
        index: Int,
        movieBookingInfo: MovieBookingInfo
    ) {
        seatGroup = seatGroup.remove(newSeat)
        ticketBundle = ticketBundle.remove(Ticket(newSeat.getSeatTier().price))
        view.setSeatColor(index, isClicked = false)
        getCanAddSeat()
        view.setTicketPrice(
            ticketBundle.calculateTotalPrice(movieBookingInfo.date, movieBookingInfo.time)
        )
    }

    companion object {
        private const val SEAT_ROW_INTERVAL = 4
    }
}

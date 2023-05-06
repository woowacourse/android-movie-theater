package woowacourse.movie.activity.seatPicker

import android.os.Bundle
import android.widget.TextView
import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieBookingSeatInfo
import com.woowacourse.domain.seat.Seat
import com.woowacourse.domain.seat.SeatColumn
import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.seat.SeatRow
import com.woowacourse.domain.ticket.Ticket
import com.woowacourse.domain.ticket.TicketBundle
import woowacourse.movie.mapper.toPresentation
import woowacourse.movie.movie.MovieBookingInfoUiModel
import woowacourse.movie.movie.toDomain
import woowacourse.movie.movie.toPresentation

class SeatPickerPresenter(
    val view: SeatPickerContract.View,
    val movieBookingInfo: MovieBookingInfo
) : SeatPickerContract.Presenter {

    override val isEnoughTicketNum: Boolean
        get() = !seatGroup.canAdd(ticketBundle.count)
    private var seatGroup = SeatGroup()
    private var ticketBundle: TicketBundle = TicketBundle(count = movieBookingInfo.ticketCount)
    private val price: Int
        get() = ticketBundle.calculateTotalPrice(
            movieBookingInfo.date,
            movieBookingInfo.time
        )

    init {
        view.initMovieTitle(movieBookingInfo.movieInfo.title)
    }

    override fun getMovieBookingSeatInfo(
        price: String,
    ) {
        val movieBookingSeatInfo = MovieBookingSeatInfo(
            movieBookingInfo,
            seatGroup.sorted().seats.map {
                SeatPickerActivity.formatSeatName(it.row.value, it.column.value)
            },
            price
        )
        view.onClickDoneBtn(movieBookingSeatInfo)
    }

    override fun setSeatGroup(seatGroup: SeatGroup) {
        this.seatGroup = seatGroup
        val seatNames = seatGroup.seats.map {
            SeatPickerActivity.formatSeatName(
                it.row.value,
                it.column.value
            )
        }
        view.setSeatGroup(seatNames)
    }

    override fun onClickSeat(index: Int, seat: TextView) {
        val newSeat =
            Seat(SeatRow(index / SEAT_ROW_INTERVAL), SeatColumn(index % SEAT_ROW_INTERVAL))
        if (seatGroup.seats.contains(newSeat)) {
            seatGroup = seatGroup.remove(newSeat)
            ticketBundle = ticketBundle.remove(Ticket(newSeat.getSeatTier().price))
            view.progressRemoveSeat(newSeat, seat)
        } else if (seatGroup.canAdd(ticketBundle.count) && !seatGroup.seats.contains(newSeat)) {
            seatGroup = seatGroup.add(newSeat)
            ticketBundle = ticketBundle.add(Ticket(newSeat.getSeatTier().price))
            view.progressAddSeat(newSeat, seat)
        }
        view.setPriceText(price)
    }

    override fun save(outState: Bundle) {
        outState.putString(SeatPickerActivity.MOVIE_TITLE, movieBookingInfo.movieInfo.title)
        outState.putString(SeatPickerActivity.TICKET_PRICE, price.toString())
        outState.putSerializable(SeatPickerActivity.PICKED_SEAT, seatGroup.toPresentation())
    }

    companion object {
        private const val SEAT_ROW_INTERVAL = 4
    }
}

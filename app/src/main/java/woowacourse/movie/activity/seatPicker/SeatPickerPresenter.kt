package woowacourse.movie.activity.seatPicker

import com.woowacourse.domain.movie.MovieBookingInfo
import com.woowacourse.domain.movie.MovieBookingSeatInfo
import com.woowacourse.domain.seat.Seat
import com.woowacourse.domain.seat.SeatColumn
import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.seat.SeatRow
import com.woowacourse.domain.ticket.Ticket
import com.woowacourse.domain.ticket.TicketBundle
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.toHistoryData
import woowacourse.movie.model.toPresentation

class SeatPickerPresenter(
    val view: SeatPickerContract.View,
    val movieBookingInfo: MovieBookingInfo,
    override val repository: MovieRepository,
) : SeatPickerContract.Presenter {

    private var seatGroup = SeatGroup()
    private var ticketBundle: TicketBundle = TicketBundle(count = movieBookingInfo.ticketCount)
    private val price: Int
        get() = ticketBundle.calculateTotalPrice(
            movieBookingInfo.date,
            movieBookingInfo.time,
        )

    override fun initMovieTitle() {
        view.setUpMovieTitle(movieBookingInfo.theaterMovie.movieInfo.movie.title)
    }

    override fun loadMovieBookingSeatInfo(
        price: String,
    ) {
        val movieBookingSeatInfo = MovieBookingSeatInfo(
            movieBookingInfo,
            seatGroup.sorted().seats.map {
                SeatPickerActivity.formatSeatName(it.row.value, it.column.value)
            },
            price,
        )

        repository.addData(movieBookingSeatInfo.toPresentation().toHistoryData())
        view.navigateToTicket(movieBookingSeatInfo)
    }

    override fun setSeatGroup(seatGroup: SeatGroup) {
        this.seatGroup = seatGroup
        val seatNames = seatGroup.seats.map {
            SeatPickerActivity.formatSeatName(
                it.row.value,
                it.column.value,
            )
        }
        view.setSeatGroup(seatNames)
    }

    override fun onClickSeat(index: Int) {
        val newSeat =
            Seat(SeatRow(index / SEAT_ROW_INTERVAL), SeatColumn(index % SEAT_ROW_INTERVAL))
        if (seatGroup.seats.contains(newSeat)) {
            seatGroup = seatGroup.remove(newSeat)
            ticketBundle = ticketBundle.remove(Ticket(newSeat.getSeatTier().price))
        } else {
            seatGroup = seatGroup.add(newSeat)
            ticketBundle = ticketBundle.add(Ticket(newSeat.getSeatTier().price))
        }
        view.setPriceText(price)
    }

    override fun loadEnoughTicketNum() {
        view.setPickDoneButtonColor(seatGroup.seats.size == ticketBundle.count)
    }

    companion object {
        const val SEAT_ROW_INTERVAL = 4
    }
}

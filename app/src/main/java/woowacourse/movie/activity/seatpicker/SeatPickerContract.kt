package woowacourse.movie.activity.seatpicker

import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.ticket.TicketBundle
import woowacourse.movie.movie.MovieBookingSeatInfo

interface SeatPickerContract {
    interface View {
        var presenter: Presenter

        fun initSeat()
        fun setMovieTitle(title: String)
        fun setTicketPrice(price: Int)
        fun showDialog()
        fun setSeatColor(index: Int, isClicked: Boolean)
        fun setPickDoneButtonColor(isPickDone: Boolean)
        fun setMovieAlarm(movieBookingSeatInfo: MovieBookingSeatInfo)
        fun formatSeatName(index: Int, rowIndex: Int): String
    }

    interface Presenter {
        var seatGroup: SeatGroup
        var ticketBundle: TicketBundle
        fun initPage()
        fun onPickDoneButtonClicked()
        fun onSeatClicked(index: Int)
        fun getCanAddSeat()
    }
}

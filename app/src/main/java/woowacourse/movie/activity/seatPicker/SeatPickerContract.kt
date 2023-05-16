package woowacourse.movie.activity.seatPicker

import android.os.Bundle
import android.widget.TextView
import com.woowacourse.domain.movie.MovieBookingSeatInfo
import com.woowacourse.domain.seat.Seat
import com.woowacourse.domain.seat.SeatGroup
import woowacourse.movie.data.MovieRepository

interface SeatPickerContract {

    interface View {
        var presenter: Presenter
        fun setUpMovieTitle(title: String)
        fun navigateToTicket(movieBookingSeatInfo: MovieBookingSeatInfo)
        fun setSeatGroup(seatNames: List<String>)
        fun progressRemoveSeat(
            newSeat: Seat,
            seat: TextView,
        )

        fun progressAddSeat(
            newSeat: Seat,
            seat: TextView,
        )

        fun setPriceText(price: Int)
        fun setPickDoneButtonColor(isEnoughTicketNum: Boolean)
    }

    interface Presenter {
        val repository: MovieRepository
        fun initMovieTitle()
        fun loadMovieBookingSeatInfo(price: String)
        fun loadEnoughTicketNum()
        fun setSeatGroup(seatGroup: SeatGroup)
        fun onClickSeat(index: Int, seat: TextView)
        fun save(outState: Bundle)
    }
}

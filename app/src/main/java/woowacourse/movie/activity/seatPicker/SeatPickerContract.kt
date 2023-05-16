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
    }

    interface Presenter {
        val repository: MovieRepository
        val isEnoughTicketNum: Boolean
        fun initMovieTitle()
        fun loadMovieBookingSeatInfo(price: String)
        fun setSeatGroup(seatGroup: SeatGroup)
        fun onClickSeat(index: Int, seat: TextView)
        fun save(outState: Bundle)
    }
}

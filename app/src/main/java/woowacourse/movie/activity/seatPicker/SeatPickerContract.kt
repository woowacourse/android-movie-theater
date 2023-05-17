package woowacourse.movie.activity.seatPicker

import com.woowacourse.domain.movie.MovieBookingSeatInfo
import com.woowacourse.domain.seat.SeatGroup
import woowacourse.movie.data.MovieRepository

interface SeatPickerContract {

    interface View {
        var presenter: Presenter
        fun setUpMovieTitle(title: String)
        fun navigateToTicket(movieBookingSeatInfo: MovieBookingSeatInfo)
        fun setSeatGroup(seatNames: List<String>)

        fun setPriceText(price: Int)
        fun setPickDoneButtonColor(isEnoughTicketNum: Boolean)
    }

    interface Presenter {
        val repository: MovieRepository
        fun initMovieTitle()
        fun loadMovieBookingSeatInfo(price: String)
        fun loadEnoughTicketNum()
        fun setSeatGroup(seatGroup: SeatGroup)
        fun onClickSeat(index: Int)
    }
}

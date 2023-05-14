package woowacourse.movie.activity.moviedetail

import woowacourse.movie.movie.Movie

interface MovieDetailContract {
    interface View {
        var presenter: Presenter
        fun setTicketCountText(count: Int)
        fun startSeatPickerPage(movieData: Movie)
    }

    interface Presenter {
        fun isMinTicketCount(): Boolean
        fun getTicketCount(): Int
        fun addTicket()
        fun subTicket()
        fun onClickMoveSeatPickerPageButton()
    }
}

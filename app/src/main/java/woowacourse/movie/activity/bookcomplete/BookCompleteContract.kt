package woowacourse.movie.activity.bookcomplete

import woowacourse.movie.movie.MovieBookingSeatInfo

interface BookCompleteContract {
    interface View {
        var presenter: Presenter

        fun initMovieTitle(movieTitle: String)
        fun initBookDate(bookingTime: String)
        fun initBookPersonCount(ticketCount: Int, seats: List<String>)
        fun initBookTotalPrice(totalPrice: String)
        fun showMessageIfDummyData()
    }

    interface Presenter {
        fun initBookCompletePage(movieBookingSeatInfo: MovieBookingSeatInfo)
        fun progressIfDummyData(movieBookingSeatInfo: MovieBookingSeatInfo)
    }
}

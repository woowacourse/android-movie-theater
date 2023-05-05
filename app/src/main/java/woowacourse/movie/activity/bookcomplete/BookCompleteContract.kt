package woowacourse.movie.activity.bookcomplete

import woowacourse.movie.movie.MovieBookingSeatInfo

interface BookCompleteContract {
    interface View {
        var presenter: Presenter

        fun progressIfDummyData(movieBookingSeatInfo: MovieBookingSeatInfo)
        fun initMovieTitle(movieTitle: String)
        fun initBookDate(bookingTime: String)
        fun initBookPersonCount(ticketCount: Int, seats: List<String>)
        fun initBookTotalPrice(totalPrice: String)
    }
    interface Presenter {
        fun initBookCompletePage(movieBookingSeatInfo: MovieBookingSeatInfo)
    }
}

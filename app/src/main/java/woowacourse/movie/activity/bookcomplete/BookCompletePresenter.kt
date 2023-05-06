package woowacourse.movie.activity.bookcomplete

import woowacourse.movie.movie.MovieBookingSeatInfo

class BookCompletePresenter(
    private val view: BookCompleteContract.View,
) : BookCompleteContract.Presenter {

    override fun initBookCompletePage(movieBookingSeatInfo: MovieBookingSeatInfo) {
        view.initMovieTitle(movieBookingSeatInfo.movieBookingInfo.title)
        view.initBookDate(movieBookingSeatInfo.movieBookingInfo.formatBookingTime())
        view.initBookPersonCount(
            movieBookingSeatInfo.movieBookingInfo.ticketCount,
            movieBookingSeatInfo.seats
        )
        view.initBookTotalPrice(movieBookingSeatInfo.totalPrice)
    }
}

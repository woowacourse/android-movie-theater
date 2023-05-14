package woowacourse.movie.activity.bookcomplete

import woowacourse.movie.movie.MovieBookingSeatInfo

class BookCompletePresenter(
    private val view: BookCompleteContract.View,
    private val movieBookingSeatInfo: MovieBookingSeatInfo,
) : BookCompleteContract.Presenter {

    override fun progressIfDummyData() {
        if (movieBookingSeatInfo == MovieBookingSeatInfo.dummyData) {
            view.showMessageIfDummyData()
        }
    }

    override fun initBookCompletePage() {
        view.initMovieTitle(movieBookingSeatInfo.movieBookingInfo.title)
        view.initBookDate(movieBookingSeatInfo.movieBookingInfo.formatBookingTime())
        view.initBookPersonCount(
            movieBookingSeatInfo.movieBookingInfo.ticketCount,
            movieBookingSeatInfo.seats
        )
        view.initBookTotalPrice(movieBookingSeatInfo.totalPrice)
    }
}

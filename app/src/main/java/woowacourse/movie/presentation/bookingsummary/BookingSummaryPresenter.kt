package woowacourse.movie.presentation.bookingsummary

import woowacourse.movie.domain.model.movie.MovieTicket

class BookingSummaryPresenter(
    private val view: BookingSummaryContract.View,
) : BookingSummaryContract.Presenter {
    private lateinit var movieTicket: MovieTicket

    override fun initializeBookingSummary(movieTicket: MovieTicket) {
        this.movieTicket = movieTicket
        view.showTicket(movieTicket)
        view.showCancelableTime(CANCELABLE_TIME)
    }

    companion object {
        private const val CANCELABLE_TIME = 15
    }
}

package woowacourse.movie.moviebooked

import woowacourse.movie.domain.BookingStatus

class MovieBookedPresenter(
    private val view: MovieBooked.View,
) : MovieBooked.Presenter {
    override fun loadBookedStatus(bookingStatus: BookingStatus) {
        view.showBookedStatus(bookingStatus)
    }
}

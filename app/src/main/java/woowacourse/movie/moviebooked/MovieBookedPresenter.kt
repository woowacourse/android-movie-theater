package woowacourse.movie.moviebooked

import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater

class MovieBookedPresenter(
    private val view: MovieBooked.View,
) : MovieBooked.Presenter {
    override fun loadBookedStatus(bookingStatus: BookingStatus, theater: Theater) {
        view.showBookedStatus(bookingStatus, theater)
    }
}

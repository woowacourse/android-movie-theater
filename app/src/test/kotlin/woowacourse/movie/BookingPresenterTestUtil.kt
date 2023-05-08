package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.main.MainModelHandler
import woowacourse.movie.ui.booking.BookingContract
import woowacourse.movie.ui.booking.BookingPresenter
import java.time.LocalDateTime

fun BookingPresenter(view: BookingContract.View): BookingPresenter {

    return BookingPresenter(
        view = view,
        repository = MainModelHandler,
        movieId = 1,
        theaterId = 0
    )
}

fun BookedMovie(
    bookedDateTime: LocalDateTime
): BookedMovie {

    return BookedMovie(
        movieId = 1,
        theaterId = 0,
        ticketCount = 1,
        bookedDateTime = bookedDateTime
    )
}

fun ignoreDates(view: BookingContract.View) {
    every { view.setDates(any()) } just Runs
}

fun ignoreTimes(view: BookingContract.View) {
    every { view.setTimes(any()) } just Runs
}

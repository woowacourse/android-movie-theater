package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import woowacourse.movie.model.main.MainModelHandler
import woowacourse.movie.ui.booking.BookingContract
import woowacourse.movie.ui.booking.BookingPresenter

fun BookingPresenter(view: BookingContract.View): BookingPresenter {

    return BookingPresenter(
        view = view,
        repository = MainModelHandler,
        movieId = 1,
        theaterId = 0
    )
}

fun ignoreDates(view: BookingContract.View) {
    every { view.setDates(any()) } just Runs
}

fun ignoreTimes(view: BookingContract.View) {
    every { view.setTimes(any()) } just Runs
}

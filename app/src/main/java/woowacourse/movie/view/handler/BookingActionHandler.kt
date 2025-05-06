package woowacourse.movie.view.handler

import woowacourse.movie.view.booking.BookingActivity
import woowacourse.movie.view.booking.BookingContract

class BookingActionHandler(
    private val presenter: BookingContract.Presenter,
) {
    fun increase() {
        presenter.increasePeopleCount(BookingActivity.Companion.MAX_SEAT)
    }

    fun decrease() {
        presenter.decreasePeopleCount()
    }

    fun booking() {
        presenter.loadBooking()
    }
}

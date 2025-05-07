package woowacourse.movie.view.booking

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

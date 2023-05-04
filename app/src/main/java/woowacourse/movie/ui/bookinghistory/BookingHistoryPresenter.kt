package woowacourse.movie.ui.bookinghistory

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val repository: BookingHistoryRepository
) : BookingHistoryContract.Presenter {

    override fun initBookingHistories() {
        val bookingHistories = repository.loadBookingHistory()

        view.initAdapter(bookingHistories)
    }
}

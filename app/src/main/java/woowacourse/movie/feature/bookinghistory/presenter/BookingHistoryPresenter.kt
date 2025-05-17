package woowacourse.movie.feature.bookinghistory.presenter

import woowacourse.movie.domain.repository.BookingHistoryRepository
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.model.BookingInfoUiModel
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val repository: BookingHistoryRepository,
) : BookingHistoryContract.Presenter {
    override fun prepareBookingHistory() {
        thread {
            val bookingHistory = repository.fetchAllBookingHistory()
            view.showBookingHistory(bookingHistory)
        }
    }

    override fun selectBookingHistory(bookingHistory: BookingInfoUiModel) {
        view.navigateToBookingDetail(bookingHistory)
    }
}

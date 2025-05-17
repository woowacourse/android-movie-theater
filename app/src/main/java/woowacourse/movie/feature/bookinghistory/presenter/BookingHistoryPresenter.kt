package woowacourse.movie.feature.bookinghistory.presenter

import woowacourse.movie.domain.repository.BookingHistoryRepository
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val repository: BookingHistoryRepository,
) : BookingHistoryContract.Presenter {
    override fun prepareBookingHistory() {
        repository.fetchAllBookingHistory { bookingHistory ->
            view.showBookingHistory(bookingHistory)
        }
    }

    override fun selectBookingHistory(bookingHistory: BookingInfoUiModel) {
        view.navigateToBookingDetail(bookingHistory)
    }
}

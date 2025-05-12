package woowacourse.movie.feature.bookinghistory.presenter

import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
) : BookingHistoryContract.Presenter {
    override fun prepareBookingHistory() {
        view.showBookingHistory()
    }

    override fun selectBookingHistory(bookingHistory: BookingInfoUiModel) {
        view.navigateToBookingDetail(bookingHistory)
    }
}

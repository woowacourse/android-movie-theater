package woowacourse.movie.feature.bookinghistory.presenter

import woowacourse.movie.domain.repository.BookingRepository
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val bookingRepository: BookingRepository,
) : BookingHistoryContract.Presenter {
    override fun getBookingHistory() {
        val bookingHistory: List<BookingInfoUiModel> = bookingRepository.fetchBookingHistory().map { it.toUi() }
        view.showBookingHistory(bookingHistory)
    }

    override fun selectBookingHistory(bookingInfo: BookingInfoUiModel) {
        view.navigateToBookingComplete(bookingInfo)
    }
}

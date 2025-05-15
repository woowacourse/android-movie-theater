package woowacourse.movie.feature.bookinghistory.presenter

import woowacourse.movie.domain.repository.BookingRepository
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val bookingRepository: BookingRepository,
) : BookingHistoryContract.Presenter {
    override fun getBookingHistory() {
        thread {
            val bookingHistory: List<BookingInfoUiModel> = bookingRepository.fetchBookingHistory().map { it.toUi() }
            view.showBookingHistory(bookingHistory)
        }
    }

    override fun selectBookingHistory(bookingInfo: BookingInfoUiModel) {
        view.navigateToBookingComplete(bookingInfo)
    }
}

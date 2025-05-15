package woowacourse.movie.feature.bookinghistory.contract

import woowacourse.movie.feature.model.BookingInfoUiModel

interface BookingHistoryContract {
    interface View {
        fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>)

        fun navigateToBookingComplete(bookingInfo: BookingInfoUiModel)
    }

    interface Presenter {
        fun getBookingHistory()

        fun selectBookingHistory(bookingInfo: BookingInfoUiModel)
    }
}

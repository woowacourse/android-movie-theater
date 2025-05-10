package woowacourse.movie.feature.bookinghistory.contract

import woowacourse.movie.feature.model.BookingInfoUiModel

interface BookingHistoryContract {
    interface View {
        fun showBookingHistory(bookingHistory: List<BookingInfoUiModel>)

        fun navigateToBookingDetail(bookingHistory: BookingInfoUiModel)
    }

    interface Presenter {
        fun prepareBookingHistory()

        fun selectBookingHistory(bookingHistory: BookingInfoUiModel)
    }
}

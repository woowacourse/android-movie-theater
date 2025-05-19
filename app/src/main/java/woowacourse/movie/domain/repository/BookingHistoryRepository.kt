package woowacourse.movie.domain.repository

import woowacourse.movie.feature.model.BookingInfoUiModel

interface BookingHistoryRepository {
    fun fetchAllBookingHistory(callback: (List<BookingInfoUiModel>) -> Unit)

    fun saveBookingHistory(
        bookingHistory: BookingInfoUiModel,
        callback: (BookingInfoUiModel) -> Unit,
    )
}

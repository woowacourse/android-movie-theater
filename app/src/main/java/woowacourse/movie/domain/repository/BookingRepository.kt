package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.BookingInfo

interface BookingRepository {
    fun fetchBookingHistory(): List<BookingInfo>

    fun saveBookingHistory(bookingInfo: BookingInfo)
}

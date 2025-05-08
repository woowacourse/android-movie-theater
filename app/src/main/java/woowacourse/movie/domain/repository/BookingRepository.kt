package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.BookingInfo

interface BookingRepository {
    fun getAll(): List<BookingInfo>

    fun insertAll(vararg bookingInfo: BookingInfo)
}

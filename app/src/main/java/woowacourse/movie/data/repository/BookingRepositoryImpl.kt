package woowacourse.movie.data.repository

import woowacourse.movie.data.dao.BookingDao
import woowacourse.movie.data.mapper.toData
import woowacourse.movie.data.mapper.toDomain
import woowacourse.movie.domain.model.BookingInfo
import woowacourse.movie.domain.repository.BookingRepository

class BookingRepositoryImpl(
    private val dao: BookingDao,
) : BookingRepository {
    override fun fetchBookingHistory(): List<BookingInfo> = dao.getAll().map { it.toDomain() }

    override fun saveBookingHistory(bookingInfo: BookingInfo) {
        dao.insert(bookingInfo.toData())
    }
}

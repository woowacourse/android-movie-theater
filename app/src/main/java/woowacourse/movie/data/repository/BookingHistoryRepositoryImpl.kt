package woowacourse.movie.data.repository

import woowacourse.movie.data.BookingHistoryDetailsDao
import woowacourse.movie.data.toEntity
import woowacourse.movie.data.toUiModel
import woowacourse.movie.domain.repository.BookingHistoryRepository
import woowacourse.movie.feature.model.BookingInfoUiModel
import kotlin.concurrent.thread

class BookingHistoryRepositoryImpl(
    private val dao: BookingHistoryDetailsDao,
) : BookingHistoryRepository {
    override fun fetchAllBookingHistory(callback: (List<BookingInfoUiModel>) -> Unit) {
        thread {
            val result = dao.getAll().map { it.toUiModel() }
            callback(result)
        }
    }

    override fun saveBookingHistory(
        bookingHistory: BookingInfoUiModel,
        callback: (BookingInfoUiModel) -> Unit,
    ) {
        thread {
            val entity = bookingHistory.toEntity()
            dao.insertAll(entity)
            callback(bookingHistory)
        }
    }
}

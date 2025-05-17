package woowacourse.movie.data.repository

import woowacourse.movie.data.BookingHistoryDetailsDao
import woowacourse.movie.data.toUiModel
import woowacourse.movie.domain.repository.BookingHistoryRepository
import woowacourse.movie.feature.model.BookingInfoUiModel

class BookingHistoryRepositoryImpl(
    private val dao: BookingHistoryDetailsDao,
) : BookingHistoryRepository {
    override fun fetchAllBookingHistory(): List<BookingInfoUiModel> = dao.getAll().map { it.toUiModel() }
}

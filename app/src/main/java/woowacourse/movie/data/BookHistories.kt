package woowacourse.movie.data

import com.woowacourse.domain.movie.MovieBookingSeatInfo
import woowacourse.movie.model.BookingHistoryData
import woowacourse.movie.model.toHistoryData
import woowacourse.movie.model.toPresentation

object BookHistories {
    private val _bookHistories = mutableListOf<BookingHistoryData>()
    val items: List<BookingHistoryData>
        get() = _bookHistories.toList()

    fun saveBookingInfo(info: MovieBookingSeatInfo) {
        _bookHistories.add(info.toPresentation().toHistoryData())
    }
}

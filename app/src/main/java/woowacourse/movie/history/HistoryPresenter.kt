package woowacourse.movie.history

import woowacourse.movie.dto.BookingHistoryDto

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {
    override fun getHistory() = BookingHistoryDto.getHistory()
}

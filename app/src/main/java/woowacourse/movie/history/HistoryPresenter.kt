package woowacourse.movie.history

import woowacourse.movie.dto.BookingHistoryDto
import woowacourse.movie.dto.movie.BookingMovieEntity

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {

    override fun initFragment() {
        view.setUpHistoryData(BookingHistoryDto.getHistory())
    }

    override fun getHistory(): List<BookingMovieEntity> {
        return BookingHistoryDto.getHistory()
    }
}

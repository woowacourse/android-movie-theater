package woowacourse.movie.movie.history

import woowacourse.movie.movie.dto.BookingHistoryDto
import woowacourse.movie.movie.dto.movie.BookingMovieEntity

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {

    override fun initFragment() {
        view.setUpHistoryData(BookingHistoryDto.getHistory())
    }

    override fun getHistory(): List<BookingMovieEntity> {
        return BookingHistoryDto.getHistory()
    }
}

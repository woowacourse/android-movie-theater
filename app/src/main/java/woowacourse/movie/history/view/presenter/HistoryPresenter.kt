package woowacourse.movie.history.view.presenter

import woowacourse.movie.history.view.contract.HistoryContract
import woowacourse.movie.history.model.BookingHistoryUIModel

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {
    override fun getHistory() = BookingHistoryUIModel.getHistory()
}

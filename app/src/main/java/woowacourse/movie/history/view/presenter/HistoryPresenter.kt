package woowacourse.movie.history.view.presenter

import woowacourse.movie.history.model.BookingHistoryUIModel
import woowacourse.movie.history.view.contract.HistoryContract

class HistoryPresenter(private val view: HistoryContract.View) : HistoryContract.Presenter {
    override fun getHistory() = BookingHistoryUIModel.getHistory()
}

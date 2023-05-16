package woowacourse.movie.activity.bookComplete

import woowacourse.movie.model.BookingHistoryData
import woowacourse.movie.model.TicketData

class BookCompletePresenter(
    val view: BookCompleteContract.View,
    override val data: TicketData,
) : BookCompleteContract.Presenter {

    init {
        if (data == BookingHistoryData.dummyData) {
            view.displayToastIfDummyData()
        }
    }

    override fun initView() {
        view.initView(data)
    }
}

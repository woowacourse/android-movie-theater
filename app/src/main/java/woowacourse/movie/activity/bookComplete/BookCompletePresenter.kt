package woowacourse.movie.activity.bookComplete

import woowacourse.movie.model.MovieBookingSeatInfoUIModel
import woowacourse.movie.model.TicketData

class BookCompletePresenter(
    val view: BookCompleteContract.View,
    override val data: TicketData,
) : BookCompleteContract.Presenter {

    override fun initView() {
        view.initView(data)
    }

    override fun hasDummyData() {
        if (data == MovieBookingSeatInfoUIModel.dummyData) {
            view.displayToastIfDummyData()
        }
    }
}

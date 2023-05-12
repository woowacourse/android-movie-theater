package woowacourse.movie.presentation.ui.main.fragments.history.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

interface HistoryContract {
    interface View : BaseContract.View {
        fun showMoreHistory(item: ListItem)
        fun showMoreHistories(items: List<ListItem>)
        fun showTicketingResultScreen(item: ListItem)
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun addHistory(item: Reservation)
        abstract fun loadHistories()
        abstract fun handleItem(item: ListItem)
    }
}

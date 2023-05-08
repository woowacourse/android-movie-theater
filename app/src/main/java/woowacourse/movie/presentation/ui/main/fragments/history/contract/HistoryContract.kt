package woowacourse.movie.presentation.ui.main.fragments.history.contract

import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun showMoreHistory(item: ListItem)
        fun showMoreHistories(items: List<ListItem>)
        fun showTicketingResultScreen(item: ListItem)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun addHistory(item: Reservation)
        abstract fun loadHistories()
        abstract fun handleItem(item: ListItem)
    }
}

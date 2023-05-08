package woowacourse.movie.presentation.views.main.fragments.history.contract

import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun showMoreHistory(item: ListItem)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun addHistory(item: Reservation)
        abstract fun loadHistories(): List<Reservation>
    }
}

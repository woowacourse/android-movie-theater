package woowacourse.movie.presentation.activities.main.fragments.history.contract

import woowacourse.movie.presentation.model.movieitem.ListItem

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun showExtraHistories(items: List<ListItem>)
        fun showDetails(item: ListItem)
    }

    abstract class Presenter {
        protected val view: View

        abstract fun loadHistories()
        abstract fun onClickItem(item: ListItem)
    }
}

package woowacourse.movie.presentation.views.main.fragments.history.contract

import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun showHistories(items: List<ListItem>)
        fun showMoreHistory(item: ListItem)
        fun showDetails(item: ListItem)
    }

    abstract class Presenter {
        private var view: View? = null

        open fun attach(view: View) {
            this.view = view
        }

        open fun detach() {
            this.view = null
        }

        protected fun requireView(): View =
            view ?: throw IllegalStateException("View is not attached")

        abstract fun addHistory(item: Reservation)
        abstract fun loadHistories()
        abstract fun onClickItem(item: ListItem)
    }
}

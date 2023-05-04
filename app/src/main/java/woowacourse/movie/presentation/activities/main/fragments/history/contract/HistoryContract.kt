package woowacourse.movie.presentation.activities.main.fragments.history.contract

import woowacourse.movie.presentation.model.movieitem.ListItem

interface HistoryContract {
    interface View {
        val presenter: Presenter

        fun showExtraHistories(items: List<ListItem>)
        fun showAddedHistory(item: List<ListItem>)
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

        abstract fun loadHistories()
        abstract fun onClickItem(item: ListItem)
    }
}

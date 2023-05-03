package woowacourse.movie.presentation.activities.main.fragments.history

import woowacourse.movie.presentation.model.item.ListItem

interface HistoryContract {
    interface View {
        val presenter: Presenter
        fun setAdapterListener(item: ListItem)
    }

    interface Presenter {
        fun onClicked(item: ListItem)
    }
}

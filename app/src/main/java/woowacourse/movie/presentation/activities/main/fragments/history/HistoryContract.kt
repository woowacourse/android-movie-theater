package woowacourse.movie.presentation.activities.main.fragments.history

import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Reservation

interface HistoryContract {
    interface View {
        val presenter: Presenter
        fun setAdapterListener(item: ListItem)
        fun setAdapterData(items: List<Reservation>)
    }

    interface Presenter {
        fun onClicked(item: ListItem)
        fun getData()
    }
}

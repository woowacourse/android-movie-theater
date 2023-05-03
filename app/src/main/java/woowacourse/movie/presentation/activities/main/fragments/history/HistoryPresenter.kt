package woowacourse.movie.presentation.activities.main.fragments.history

import woowacourse.movie.presentation.model.item.ListItem

class HistoryPresenter(
    val view: HistoryContract.View,
) : HistoryContract.Presenter {
    override fun onClicked(item: ListItem) {
        view.setAdapterListener(item)
    }
}

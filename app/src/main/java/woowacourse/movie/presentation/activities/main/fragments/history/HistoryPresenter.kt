package woowacourse.movie.presentation.activities.main.fragments.history

import woowacourse.movie.presentation.model.item.ListItem

class HistoryPresenter(
    val view: HistoryContract.View,
    val db: HistoryDbHelper,
) : HistoryContract.Presenter {

    override fun onClicked(item: ListItem) {
        view.setAdapterListener(item)
    }

    override fun getData() {
        view.setAdapterData(db.getData())
    }
}

package woowacourse.movie.presentation.activities.main.fragments.history

import woowacourse.movie.presentation.model.item.ListItem

class HistoryPresenter(
    val view: HistoryContract.View,
    val db: HistoryDbHelper,
) : HistoryContract.Presenter {

    override fun onItemClicked(item: ListItem) {
        view.setAdapterListener(item)
    }

    override fun setAdapterDataFromDb() {
        val data = db.getData()
        view.setAdapterData(data)
    }
}

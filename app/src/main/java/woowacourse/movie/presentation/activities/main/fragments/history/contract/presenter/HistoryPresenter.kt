package woowacourse.movie.presentation.activities.main.fragments.history.contract.presenter

import woowacourse.movie.presentation.activities.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

class HistoryPresenter(view: HistoryContract.View) : HistoryContract.Presenter(view) {
    private val loadedHistories = mutableSetOf<ListItem>()

    override fun loadHistories() {
        val newHistories = Reservation.provideDummy()

        loadedHistories.addAll(newHistories)
        view.showExtraHistories(newHistories)
    }

    override fun onClickItem(item: ListItem) {
        when (item) {
            is Reservation -> view.showDetails(item)
        }
    }
}

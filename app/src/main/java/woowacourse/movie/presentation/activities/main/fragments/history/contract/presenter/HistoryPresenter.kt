package woowacourse.movie.presentation.activities.main.fragments.history.contract.presenter

import woowacourse.movie.presentation.activities.main.fragments.history.contract.HistoryContract
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem

class HistoryPresenter : HistoryContract.Presenter() {
    private val loadedHistories = mutableSetOf<ListItem>()

    override fun attach(view: HistoryContract.View) {
        super.attach(view)
        loadHistories()
    }

    override fun loadHistories() {
        val newHistories = Reservation.provideDummy()

        loadedHistories.addAll(newHistories)
        requireView().showExtraHistories(newHistories)
    }

    override fun onClickItem(item: ListItem) {
        when (item) {
            is Reservation -> requireView().showDetails(item)
        }
    }
}

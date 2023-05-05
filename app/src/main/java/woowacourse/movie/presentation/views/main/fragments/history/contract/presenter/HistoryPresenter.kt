package woowacourse.movie.presentation.views.main.fragments.history.contract.presenter

import com.woowacourse.data.repository.history.HistoryRepository
import woowacourse.movie.domain.model.reservation.DomainReservation
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.views.main.fragments.history.contract.HistoryContract

class HistoryPresenter(
    private val historyRepository: HistoryRepository,
) : HistoryContract.Presenter() {
    private val loadedHistories = mutableSetOf<DomainReservation>()

    override fun attach(view: HistoryContract.View) {
        super.attach(view)
        loadHistories()
    }

    override fun addHistory(item: Reservation) {
        loadedHistories.add(item.toDomain())
        requireView().showMoreHistory(item)
    }

    override fun loadHistories() {
        val newHistories = historyRepository.getAll()

        loadedHistories.addAll(newHistories)
        requireView().showHistories(newHistories.map { it.toPresentation() })
    }

    override fun onClickItem(item: ListItem) {
        when (item) {
            is Reservation -> requireView().showDetails(item)
        }
    }
}

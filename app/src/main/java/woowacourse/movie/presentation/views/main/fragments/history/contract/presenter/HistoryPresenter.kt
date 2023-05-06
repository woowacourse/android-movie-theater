package woowacourse.movie.presentation.views.main.fragments.history.contract.presenter

import com.woowacourse.data.repository.history.HistoryRepository
import woowacourse.movie.domain.model.reservation.DomainReservation
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.views.main.fragments.history.contract.HistoryContract

class HistoryPresenter(
    private val historyRepository: HistoryRepository,
) : HistoryContract.Presenter() {
    private val loadedHistories = mutableSetOf<DomainReservation>()

    override fun addHistory(item: Reservation) {
        loadedHistories.add(item.toDomain())
        requireView().showMoreHistory(item)
    }

    override fun loadHistories(): List<Reservation> {
        val newHistories = historyRepository.getAll()

        loadedHistories.addAll(newHistories)
        return newHistories.map { it.toPresentation() }
    }
}

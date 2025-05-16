package woowacourse.movie.presentation.history

import woowacourse.movie.RepositoryProvider
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.presentation.common.model.toUiModel

class ReservationHistoryPresenter private constructor(
    private val view: ReservationHistoryContract.View,
    private val reservationRepository: ReservationRepository,
) : ReservationHistoryContract.Presenter {
    override fun fetchData() {
        reservationRepository.getAll { histories ->
            view.showReservationHistory(histories.map { it.toUiModel() })
        }
    }

    companion object {
        fun create(
            view: ReservationHistoryContract.View,
            reservationRepository: ReservationRepository = RepositoryProvider.reservationRepository
        ): ReservationHistoryPresenter {
            return ReservationHistoryPresenter(view, reservationRepository)
        }
    }
}

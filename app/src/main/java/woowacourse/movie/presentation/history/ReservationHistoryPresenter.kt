package woowacourse.movie.presentation.history

import woowacourse.movie.AppProvider
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.presentation.common.model.toUiModel

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationRepository: ReservationRepository = AppProvider.reservationRepository,
) : ReservationHistoryContract.Presenter {
    override fun fetchData() {
        reservationRepository.getAll { histories ->
            view.showReservationHistory(histories.map { it.toUiModel() })
        }
    }
}

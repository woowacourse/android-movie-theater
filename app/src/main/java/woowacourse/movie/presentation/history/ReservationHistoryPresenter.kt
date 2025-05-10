package woowacourse.movie.presentation.history

import woowacourse.movie.data.ReservationRepositoryImpl
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.presentation.common.model.toUiModel
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationRepository: ReservationRepository = ReservationRepositoryImpl(),
) : ReservationHistoryContract.Presenter {
    override fun fetchData() {
        thread {
            val reservations = reservationRepository.getAll()
            view.showReservationHistory(reservations.map { it.toUiModel() })
        }
    }
}

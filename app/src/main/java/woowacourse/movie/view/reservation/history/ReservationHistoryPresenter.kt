package woowacourse.movie.view.reservation.history

import woowacourse.movie.Repository.ReservationHistoryRepository
import woowacourse.movie.Repository.ReservationHistoryRepositoryImpl

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationHistoryRepository: ReservationHistoryRepository,
) : ReservationHistoryContract.Presenter {
    override fun fetchReservationHistory() {
        reservationHistoryRepository.getAllReservationHistories { result ->
            view.showReservationHistory(result)
        }
    }

    companion object {
        fun newInstance(view: ReservationHistoryContract.View): ReservationHistoryPresenter {
            val repository = ReservationHistoryRepositoryImpl.newInstance()
            return ReservationHistoryPresenter(view, repository)
        }
    }
}

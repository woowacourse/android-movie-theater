package woowacourse.movie.presentation.ui.main.history

import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val repository: ReservationRepository,
) : ReservationHistoryContract.Presenter {
    override fun fetchData() {
        thread(start = true) {
            repository.findAll().onSuccess {
                view.showReservations(it)
            }
        }
    }
}

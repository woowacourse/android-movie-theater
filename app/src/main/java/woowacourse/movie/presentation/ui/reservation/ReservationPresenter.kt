package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val repository: ReservationRepository,
) : ReservationContract.Presenter {
    private lateinit var _reservation: Reservation
    private val reservation
        get() = _reservation

    override fun loadReservation(reservationId: Long) {
        thread {
            repository.findByReservationId(reservationId)
                .onSuccess { reservation ->
                    _reservation = reservation
                    view.showReservation(this.reservation)
                }.onFailure { e ->
                    view.terminateOnError(e)
                }
        }
    }
}

package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val repository: ReservationRepository,
    private val theaterRepository: ScreenRepository,
) : ReservationContract.Presenter {
    private var _reservationModel: ReservationModel = ReservationModel()
    val reservationModel
        get() = _reservationModel

    override fun loadReservation(id: Int) {
        repository.findByReservationId(id).onSuccess { reservation ->
            theaterRepository.findTheaterNameById(reservation.theaterId).onSuccess { theaterName ->
                view.showReservation(reservation, theaterName)
                _reservationModel = reservationModel.copy(theaterName = theaterName, reservation = reservation)
            }
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> {
                    view.showToastMessage(e)
                    view.back()
                }

                else -> {
                    view.showToastMessage(e)
                    view.back()
                }
            }
        }
    }
}

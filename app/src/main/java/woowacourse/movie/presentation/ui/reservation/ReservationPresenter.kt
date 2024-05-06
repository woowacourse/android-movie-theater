package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val repository: ReservationRepository,
    private val theaterRepository: ScreenRepository,
) : ReservationContract.Presenter {
    private var _reservationModel: ReservationModel = ReservationModel()
    private val reservationModel
        get() = _reservationModel

    override fun loadReservation(id: Int) {
        repository.findByReservationId(id).onSuccess { reservation ->
            findTheaterName(reservation)
        }.onFailure { e ->
            onLoadError(e)
        }
    }

    private fun findTheaterName(reservation: Reservation) {
        theaterRepository.findTheaterNameById(reservation.theaterId).onSuccess { theaterName ->
            updateReservation(theaterName, reservation)
        }.onFailure { e ->
            onLoadError(e)
        }
    }

    private fun updateReservation(
        theaterName: String,
        reservation: Reservation,
    ) {
        _reservationModel =
            reservationModel.copy(theaterName = theaterName, reservation = reservation)
        view.showReservation(reservationModel, theaterName)
    }

    private fun onLoadError(e: Throwable) {
        when (e) {
            is NoSuchElementException -> {
                view.showToastMessage(e)
                view.finishReservation()
            }

            else -> {
                view.showToastMessage(e)
                view.finishReservation()
            }
        }
    }
}

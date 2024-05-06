package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository,
) : ReservationContract.Presenter {
    private var uiModel: ReservationUiModel = ReservationUiModel()

    override fun loadReservation(id: Int) {
        reservationRepository.findByReservationId(id).onSuccess { reservation ->
            processReservation(reservation)
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> {
                    view.showToastMessage(e)
                    view.navigateBackToPrevious()
                }

                else -> {
                    view.showToastMessage(e)
                    view.navigateBackToPrevious()
                }
            }
        }
    }

    private fun processReservation(reservation: Reservation) {
        theaterRepository.findTheaterNameById(reservation.theaterId)
            .onSuccess { theaterName ->
                view.showReservation(reservation, theaterName)
                uiModel = uiModel.copy(theaterName = theaterName, reservation = reservation)
            }
            .onFailure { e ->
                when (e) {
                    is NoSuchElementException -> {
                        view.showToastMessage(e)
                        view.navigateBackToPrevious()
                    }

                    else -> {
                        view.showToastMessage(e)
                        view.navigateBackToPrevious()
                    }
                }
            }
    }
}

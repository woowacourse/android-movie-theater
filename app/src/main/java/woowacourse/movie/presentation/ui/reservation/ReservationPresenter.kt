package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository,
) : ReservationContract.Presenter {
    override fun loadReservation(id: Long) {
        thread {
            reservationRepository.findReservation(id).onSuccess { reservation ->
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
    }

    private fun processReservation(reservation: Reservation) {
        theaterRepository.findTheaterNameById(reservation.theaterId).onSuccess { theaterName ->
            view.showReservation(reservation, theaterName)
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
}

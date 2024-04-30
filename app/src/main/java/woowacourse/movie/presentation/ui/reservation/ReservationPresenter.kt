package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val repository: ReservationRepository,
    private val theaterRepository: ScreenRepository,
) : ReservationContract.Presenter {
    override fun loadReservation(id: Int) {
        repository.findByReservationId(id).onSuccess { screen ->
            theaterRepository.findTheaterNameById(screen.theaterId).onSuccess { theaterName ->
                view.showReservation(screen, theaterName)
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

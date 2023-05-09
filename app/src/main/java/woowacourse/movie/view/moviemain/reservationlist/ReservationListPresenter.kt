package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationUiModel

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val reservationRepository: ReservationRepository
) : ReservationListContract.Presenter {

    override fun loadReservationList() {
        val reservations = reservationRepository.findAll().map { it.toUiModel() }
        view.showReservationList(reservations)
    }

    override fun finishReservation(reservation: ReservationUiModel) {
        view.toReservationCompletedScreen(reservation)
    }
}

package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.data.ReservationMockRepository
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.ReservationUiModel

class ReservationListPresenter(
    private val view: ReservationListContract.View
) : ReservationListContract.Presenter {

    private val reservationRepository: ReservationRepository = ReservationMockRepository

    override fun loadReservationList() {
        val reservations = reservationRepository.findAll().map { it.toUiModel() }
        view.showReservationList(reservations)
    }

    override fun onReservationCompleted(reservation: ReservationUiModel) {
        view.openReservationCompletedActivity(reservation)
    }
}

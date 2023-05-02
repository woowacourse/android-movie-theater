package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.data.reservation.ReservationMockRepository
import woowacourse.movie.view.mapper.toUiModel

class ReservationListPresenter : ReservationListContract.Presenter {
    override fun getReservations() = ReservationMockRepository.findAll().map { it.toUiModel() }
}

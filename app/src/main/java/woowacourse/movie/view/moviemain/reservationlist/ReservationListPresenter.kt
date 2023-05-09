package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel

class ReservationListPresenter(private val view: ReservationListContract.View, private val reservationRespository: ReservationRepository) : ReservationListContract.Presenter {
    override fun fetchReservations() {
        val reservations = reservationRespository.findAll().map { it.toUiModel() }
        view.showReservations(reservations)
    }
}

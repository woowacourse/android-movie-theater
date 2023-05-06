package woowacourse.movie.view.moviemain.reservationlist

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.mapper.toUiModel

class ReservationListPresenter(private val reservationRespository: ReservationRepository) : ReservationListContract.Presenter {
    override fun getReservations() = reservationRespository.findAll().map { it.toUiModel() }
}

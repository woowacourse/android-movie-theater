package woowacourse.movie.reservationList

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.common.mapper.ReservationMapper.toView
import woowacourse.movie.common.model.ReservationsViewData
import woowacourse.movie.common.repository.ReservationRepository
import woowacourse.movie.domain.Reservation

class ReservationAdapterPresenter(
    override val view: ReservationAdapterContract.View,
    movieDao: MovieDao,
    private val reservationRepository: ReservationRepository = ReservationRepository(movieDao)
) : ReservationAdapterContract.Presenter {
    override fun setReservation() {
        val reservations = makeReservationListViewData(
            requestReservationData()
        )
        view.setAdapterData(reservations)
    }

    private fun requestReservationData(): List<Reservation> =
        reservationRepository.requestReservation()

    private fun makeReservationListViewData(reservations: List<Reservation>): ReservationsViewData {
        val reservations = reservations.map { it.toView() }
        return ReservationsViewData(reservations)
    }
}

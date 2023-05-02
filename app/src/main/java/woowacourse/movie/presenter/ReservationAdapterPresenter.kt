package woowacourse.movie.presenter

import woowacourse.movie.contract.ReservationAdapterContract
import woowacourse.movie.data.ReservationsViewData
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.repository.ReservationListRepository
import woowacourse.movie.mapper.ReservationMapper.toView

class ReservationAdapterPresenter(val view: ReservationAdapterContract.View) :
    ReservationAdapterContract.Presenter {
    private val reservationProvider: ReservationListRepository = ReservationListRepository()

    override fun requestReservationData(): List<Reservation> =
        reservationProvider.requestReservation()

    override fun setReservation() {
        val reservations = makeReservationListViewData(
            requestReservationData()
        )
        view.setAdapterData(reservations)
    }

    private fun makeReservationListViewData(reservations: List<Reservation>): ReservationsViewData {
        val reservations = reservations.map { it.toView() }
        return ReservationsViewData(reservations)
    }
}

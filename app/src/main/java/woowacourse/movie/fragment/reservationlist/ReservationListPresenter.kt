package woowacourse.movie.fragment.reservationlist

import woowacourse.movie.datasource.ReservationDataSource
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.view.data.ReservationsViewData
import woowacourse.movie.view.mapper.ReservationMapper.toView

class ReservationListPresenter(private val view: ReservationListContract.View) :
    ReservationListContract.Presenter {

    private val reservationDataSource: ReservationDataSource = ReservationDataSource()
    private val reservationRepository = ReservationRepository(reservationDataSource)

    override fun loadReservationData() {
        val reservationsViewData =
            reservationRepository.getData().map { reservation -> reservation.toView() }
                .let { ReservationsViewData(it) }
        view.setReservationData(reservationsViewData)
    }
}
